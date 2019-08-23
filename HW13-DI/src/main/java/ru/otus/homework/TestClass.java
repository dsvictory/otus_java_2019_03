package ru.otus.homework;

import java.util.List;

import ru.otus.homework.domain.User;
import ru.otus.homework.services.ORMManager;
import ru.otus.homework.services.ORMManagerImpl;
import ru.otus.homework.services.ORMTemplate;

public class TestClass {

	public static void main(String[] args) {
		
		ORMManager<User> om = new ORMManagerImpl();
		
		ORMTemplate<User> orm = om.getORM();
		
		List<User> users = orm.getAll(User.class);
		
		for (User u : users) {
			System.out.println(u);
		}

	}

}
