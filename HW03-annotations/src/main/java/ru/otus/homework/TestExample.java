package ru.otus.homework;

public class TestExample {
	
	@BeforeEachTest
	void beforeEach1() {
		System.out.println("Before each 1");
	}
	
	@BeforeEachTest
	void beforeEach2() {
		System.out.println("Before each 2");
	}
	
	@TestLaunch
	void testLaunch1() {
		System.out.println("Test 1!!!");
	}
	
	@TestLaunch
	void testLaunch2() {
		throw new UnsupportedOperationException("Специально выбрасываем исключение в тесте!");
		//System.out.println("Test 2!!!");
	}
	
	@TestLaunch
	void testLaunch3() {
		System.out.println("Test 3!!!");
	}
	
	@AfterEachTest
	void afterEach1() {
		System.out.println("After each 1");
	}
	
	@AfterEachTest
	void afterEach2() {
		System.out.println("After each 2");
	}
	
}
