package ru.otus.homework;

import java.lang.reflect.*;

public class LaunchLogging {

	public static void main(String[] args) {
		
		TestInterface testLog = IoC.createMyClass();
		testLog.calculation1(217);
		testLog.calculation2(317);
		testLog.calculation3(417);
		
	}

}
