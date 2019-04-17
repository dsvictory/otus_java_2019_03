package ru.otus.homework;

import java.util.*;

public class TestDIYarrayList {
	
	public static void main(String[] args) {
		
		//Экземпляры новой коллекции для тестов
		DIYarrayList<TestElement> testArrayList1 = new DIYarrayList<>();
		DIYarrayList<TestElement> testArrayList2 = new DIYarrayList<>();
		
		//***1***
		//Создадим коллекции ArrayList для проверки работоспособности метода addAll,
		//заполню их прописными и строчными буквами латинского алфавита,
		//упакованные в обертки TestElement
		
		//Список заглавных букв
		ArrayList<TestElement> uppercaseLettersCollection = new ArrayList<TestElement>();
		for (char i = 65; i <= 90; i++) {
			uppercaseLettersCollection.add(new TestElement(Character.toString(i)));
		}
		
		//Заполняем первую коллекцию
		testArrayList1.addAll(uppercaseLettersCollection);
		System.out.print("Первая коллекция: ");
		for (TestElement e : testArrayList1) {
			System.out.print(e);
		}
		System.out.println();
		
		//Список строчных букв
		ArrayList<TestElement> lowercaseLettersCollection = new ArrayList<TestElement>();
		for (char i = 97; i <= 122; i++) {
			lowercaseLettersCollection.add(new TestElement(Character.toString(i)));
		}
		
		//Заполняем вторую коллекцию
		testArrayList2.addAll(lowercaseLettersCollection);
		System.out.print("Вторая коллекция: ");
		for (TestElement e : testArrayList2) {
			System.out.print(e);
		}
		System.out.print("\n\n");
		
		
		//***2***
		//Скопируем первую коллекцию во вторую для проверки Collections.copy
		Collections.copy(testArrayList2, testArrayList1);
		System.out.print("Вторая коллекция после копирования из первой коллекции: ");
		for (TestElement e : testArrayList2) {
			System.out.print(e);
		} 
		System.out.print("\n\n");
		
		//***3***
		//Проверяем сортировку Collection.sort с компаратором
		Collections.sort(
				testArrayList1,
				new Comparator<TestElement>() {
					@Override
					public int compare(TestElement first, TestElement second) {
						// Задаем обратный порядок
						return first.getLetter().compareTo(second.getLetter()) * (-1);
					}
					
				}
		);
		
		System.out.print("Первая коллекция после сортировки в обратном порядке: ");
		for (TestElement e : testArrayList1) {
			System.out.print(e);
		} 
	}

}

class TestElement {
	
	private String letter;
	
	public TestElement(String letter) {
		this.letter = letter;
	}
	
	public String getLetter() {
		return this.letter;
	}
	
	@Override
	public String toString() {
		return getLetter();
	}
	
}