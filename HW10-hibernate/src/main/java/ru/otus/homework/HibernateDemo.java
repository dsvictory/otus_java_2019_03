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
		
		User user = new User();
		user.setName("Viktor");
		user.setAge(26);
		
		List<Phone> listPhone = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listPhone.add(new Phone("number " + i, user));
        }
        user.setPhones(listPhone);
		
        orm.saveEntity(user);
        
		User selectedUser = orm.getEntity(User.class, orm.getLastInsertedId());
		System.out.println(selectedUser);
		for (Phone phone : selectedUser.getPhones()) {
			System.out.println(phone);
		}
	}
	
	public void accountTest() {
		ORMTemplateImpl<Account> orm = new ORMTemplateImpl<Account>(sessionFactory);
		
		Account account = new Account();
		account.setType("Simple");
		account.setRest(new BigDecimal(308.23));
		
		List<Address> listAddress = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
        	listAddress.add(new Address("street " + i, account));
        }
        account.setAddress(listAddress);
		
        orm.saveEntity(account);
        
        Account selectedAccount = orm.getEntity(Account.class, orm.getLastInsertedId());
		System.out.println(selectedAccount);
		for (Address phone : selectedAccount.getAddress()) {
			System.out.println(phone);
		}
	}

}
