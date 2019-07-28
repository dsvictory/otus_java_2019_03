package ru.otus.homework;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ru.otus.homework.hibernate.ORMTemplateImpl;

import ru.otus.homework.model.Account;
import ru.otus.homework.model.Address;
import ru.otus.homework.model.Phone;
import ru.otus.homework.model.User;

public class HibernateDemo {

	private final SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		HibernateDemo hibernateDemo = new HibernateDemo();
		hibernateDemo.userTest();
		hibernateDemo.accountTest();
	}
	
	private HibernateDemo() {
		sessionFactory = new Configuration().configure("hibernate.cfg.xml")
			.addAnnotatedClass(User.class)
	        .addAnnotatedClass(Account.class)
	        .addAnnotatedClass(Phone.class)
	        .addAnnotatedClass(Address.class)
	        .buildSessionFactory();
	}
	
	public void userTest() {
		ORMTemplateImpl<User> orm = new ORMTemplateImpl<User>(sessionFactory);
		
		User user1 = new User();
		user1.setName("Viktor");
		user1.setAge(26);
		
		List<Phone> listPhone1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listPhone1.add(new Phone("number " + i, user1));
        }
        user1.setPhones(listPhone1);
		
        orm.saveEntity(user1);
        
        long firstId = orm.getLastInsertedId();
        
		User selectedUser = orm.getEntity(User.class, firstId);
		System.out.println(selectedUser);
		for (Phone phone : selectedUser.getPhones()) {
			System.out.println(phone);
		}
		
		User user2 = new User();
		user2.setName("NotViktor");
		user2.setAge(49);
		
		List<Phone> listPhone2 = new ArrayList<>();
        for (int i = 10; i < 13; i++) {
            listPhone2.add(new Phone("number " + i, user2));
        }
        user2.setPhones(listPhone2);
        
        orm.saveEntity(user1);
        
        User firstUser = orm.getEntity(User.class, firstId);
        
        System.out.println(firstUser);
        
	}
	
	public void accountTest() {
		ORMTemplateImpl<Account> orm = new ORMTemplateImpl<Account>(sessionFactory);
		
		Account account1 = new Account();
		account1.setType("Simple");
		account1.setRest(new BigDecimal(308.23));
		
		List<Address> listAddress1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
        	listAddress1.add(new Address("street " + i, account1));
        }
        account1.setAddress(listAddress1);
		
        orm.saveEntity(account1);
        
        long firstId = orm.getLastInsertedId();
        
        Account selectedAccount = orm.getEntity(Account.class, orm.getLastInsertedId());
		System.out.println(selectedAccount);
		for (Address phone : selectedAccount.getAddress()) {
			System.out.println(phone);
		}
		
		Account account2 = new Account();
		account2.setType("Another");
		account2.setRest(new BigDecimal(4.12));
		
		List<Address> listAddress2 = new ArrayList<>();
        for (int i = 10; i < 13; i++) {
        	listAddress2.add(new Address("street " + i, account2));
        }
        account2.setAddress(listAddress2);
        
        Account firstAccount = orm.getEntity(Account.class, firstId);
        
        System.out.println(firstAccount);
	}

}
