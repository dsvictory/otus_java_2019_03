package ru.otus.homework;

public class AnnotationsTester {

	public static void main(String[] args) {	
		run(TestExample.class);
	}
	
	public static void run(Class<?> testClass) {
		Tester tester = new Tester();
		tester.run(testClass);
	}

}
