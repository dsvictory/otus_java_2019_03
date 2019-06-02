package ru.otus.homework;

public class TestLogging implements TestInterface {
	
	@Log
	public void calculation1(int param1) {
		System.out.println("Calculation1 is launched!");
	}

	public void calculation2(int param2) {
		System.out.println("Calculation2 is launched!");
	}

	@Log
	public void calculation3(int param3) {
		System.out.println("Calculation3 is launched!");
	};
	
}
