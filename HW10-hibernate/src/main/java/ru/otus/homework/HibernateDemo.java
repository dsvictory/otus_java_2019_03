package ru.otus.homework;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import ru.otus.homework.model.Account;
import ru.otus.homework.model.Address;
import ru.otus.homework.model.Phone;
import ru.otus.homework.model.User;

public class HibernateDemo {

	private final SessionFactory sessionFactory;
	
	private long insertedUserId = 0;
	private long insertedAccountId = 0;
	
	public static void main(String[] args) {
		HibernateDemo hibernateDemo = new HibernateDemo();
		hibernateDemo.insertTest();
		hibernateDemo.selectTest();
	}
	
	private HibernateDemo() {
		Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");
		
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
		
		Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Phone.class)
                .addAnnotatedClass(Address.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
	}
	
	public void selectTest() {
		try (Session session = sessionFactory.openSession()) {
			
			User selectedUser = session.get(User.class, insertedUserId);
			System.out.println(selectedUser);
			for (Phone phone : selectedUser.getPhones()) {
				System.out.println(phone);
			}
			
			Account selectedAccount = session.get(Account.class, insertedAccountId);
			System.out.println(selectedAccount);
			for (Phone phone : selectedUser.getPhones()) {
				System.out.println(phone);
			}
			System.out.println(selectedAccount.getAddress());
			
        }
	}
	
	public void insertTest() {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			
			User user = new User();
			user.setName("Viktor");
			user.setAge(26);
			
			List<Phone> listPhone = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                listPhone.add(new Phone("number " + i, user));
            }
            user.setPhones(listPhone);
			
			insertedUserId = (long)session.save(user);
			
			Account account = new Account();
			account.setType("Simple");
			account.setRest(new BigDecimal(267.38));
			
			List<Address> listAddress = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
            	listAddress.add(new Address("street " + i, account));
            }
            account.setAddress(listAddress);
			
            insertedAccountId = (long)session.save(account);
			
			session.getTransaction().commit();	
        }
	}

}
