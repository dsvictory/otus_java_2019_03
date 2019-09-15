package ru.otus.homework.orm;

import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.*;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Repository
public class DBServiceImpl implements DBService {

	private static final Class<User> entityType = User.class;
	
	private static final String CONFIG_FILE_NAME = "hibernate.cfg.xml";
	
	private final Address address;
    private final MessageSystemContext context;
	
	private long lastInsertedId = 0;
	
	private SessionFactory sessionFactory;
	
	public DBServiceImpl(MessageSystemContext context) {
		this.sessionFactory = new org.hibernate.cfg.Configuration().configure(CONFIG_FILE_NAME)
    			.addAnnotatedClass(User.class)
    	        .buildSessionFactory();
		this.address = new Address("DBService");
		this.context = context;
	}
	
	@Override
	public User getUser(long id) {
		User user = null;
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
				
			user = session.get(entityType, id);
			
			transaction.commit();
        }
		catch (Exception ex) {
			transaction.rollback();
			System.out.println(ex.getMessage());
		}
		
		return user;
	}

	@Override
	public long saveUser(User newUser) {
		
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();

			lastInsertedId = (long)session.save(newUser);

			transaction.commit();	
			
        }
		catch (Exception ex) {
			transaction.rollback();
			System.out.println(ex.getMessage());
		}
		
		return lastInsertedId;
	}

	@Override
	public List<User> getUsers() {
		EntityManager entityManager = sessionFactory.createEntityManager();
        List<User> users = entityManager.createQuery(
                "select e from " + entityType.getSimpleName() + " e", entityType)
                .getResultList();
        return users;
	}

	@Override
	public void init() {
		context.getMessageSystem().addAddressee(this);
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public MessageSystem getMS() {
		return context.getMessageSystem();
	}

}
