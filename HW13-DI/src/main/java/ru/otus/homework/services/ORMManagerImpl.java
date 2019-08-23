package ru.otus.homework.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import ru.otus.homework.domain.User;

@Service
public class ORMManagerImpl implements ORMManager<User> {

	private final ORMTemplate<User> orm;
	
	private static final String CONFIG_FILE_NAME = "hibernate.cfg.xml";
	
	public ORMManagerImpl() {
		SessionFactory sessionFactory = new Configuration().configure(CONFIG_FILE_NAME)
    			.addAnnotatedClass(User.class)
    	        .buildSessionFactory();
    	orm = new ORMTemplateImpl<User>(sessionFactory);
    	setDefaultUsersList();
	}
	
	public ORMTemplate<User> getORM() {
		return orm;
	}
	
	private void setDefaultUsersList() {
    	User user1 = new User();
    	user1.setName("Дядя 1");
    	
    	orm.saveEntity(user1);
    	
		User user2 = new User();
		user2.setName("Дядя 2");
		
		orm.saveEntity(user2);
		
		User user3 = new User();
		user3.setName("Дядя 3");
		
		orm.saveEntity(user3);
    }
	
}
