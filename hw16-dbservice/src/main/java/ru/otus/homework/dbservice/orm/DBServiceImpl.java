package ru.otus.homework.dbservice.orm;

import org.hibernate.Transaction;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.MsgCreateUser;
import ru.otus.homework.socketSystem.SocketManager;

import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DBServiceImpl implements DBService {

	private static final Class<User> entityType = User.class;
	
	private static final String CONFIG_FILE_NAME = "hibernate.cfg.xml";
	
	private long lastInsertedId = 0;
	
	private SessionFactory sessionFactory;
	
    private SocketManager socketManager;
	
    public static final Address ADDRESS_FROM = new Address("DBService");
    
    public static final Address ADDRESS_TO = new Address("Frontend");
	
	public DBServiceImpl() {
		this.sessionFactory = new org.hibernate.cfg.Configuration().configure(CONFIG_FILE_NAME)
    			.addAnnotatedClass(User.class)
    	        .buildSessionFactory();
		this.socketManager = new SocketManager(this);
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
			
			socketManager.sendMessage(new MsgCreateUser(ADDRESS_FROM, ADDRESS_TO, getUser(lastInsertedId)));
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
	public Address getAddress() {
		return ADDRESS_FROM;
	}



}
