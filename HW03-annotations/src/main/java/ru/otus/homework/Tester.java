package ru.otus.homework;

import java.lang.reflect.*;
import java.util.ArrayList;

public class Tester {
	
	private ArrayList<Method> beforeEachMethods;
	private ArrayList<Method> testLaunchMethods;
	private ArrayList<Method> afterEachMethods;
	
	public Tester() {
		beforeEachMethods = new ArrayList<>();
		testLaunchMethods = new ArrayList<>();
		afterEachMethods = new ArrayList<>();
	}
	
	public void run(Class<?> testClass) {
			
		Method[] methods = testClass.getDeclaredMethods();
		distributeMethods(methods);
		
		if (isTestLaunchCollectionEmpty()) { 
			System.out.println("Тестов нет!");
			return; 
		}
		
		for (Method testMethod : testLaunchMethods) {
			try {
				Constructor<?> constructor = testClass.getConstructor(null);
				Object obj = constructor.newInstance(null);
				launchTest(obj, testMethod);
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	private void distributeMethods(Method[] methods) {
		for (Method method : methods) {
			if (method.getAnnotation(BeforeEachTest.class) != null) { 
				beforeEachMethods.add(method);
				continue;
			}
			if (method.getAnnotation(TestLaunch.class) != null) { 
				testLaunchMethods.add(method);
				continue;
			}
			if (method.getAnnotation(AfterEachTest.class) != null) { 
				afterEachMethods.add(method);
				continue;
			}
		}	
	}
	
	private boolean isTestLaunchCollectionEmpty() {
		return testLaunchMethods.size() == 0;
	}
	
	private boolean isBeforeEachCollectionEmpty() {
		return beforeEachMethods.size() == 0;
	}
	
	private boolean isAfterEachCollectionEmpty() {
		return afterEachMethods.size() == 0;
	}

	private void launchTest(Object testObject, Method testMethod) {
		try {
			if (!isBeforeEachCollectionEmpty()) {
				for (Method beforeEachMethod : beforeEachMethods) {
					beforeEachMethod.invoke(testObject, null);
				}
			}
			
			testMethod.invoke(testObject, null);
			
			if (!isAfterEachCollectionEmpty()) {
				for (Method afterEachMethod : afterEachMethods) {
					afterEachMethod.invoke(testObject, null);
				}
			}
		}
		catch (Exception e) {
			System.out.println("Ошибка во время теста " + testMethod.getName());
		}
	}
	
}
