package ru.otus.homework;

import java.util.*;
import com.google.gson.*;

public class JSONSerializeDemo {

	public static void main(String[] args) throws Exception {
		
		GSONAnalog analogGson = new GSONAnalog();
		Gson googleGson = new Gson();
		
		System.out.println("Мой тест с объектом сложного типа!");
		TestClass a = new TestClass();
		
		System.out.println(googleGson.toJson(a));
		System.out.println(analogGson.toJson(a));
		
		
		System.out.println();
		System.out.println("Тест от проверяющего!");
		System.out.println(googleGson.toJson(null));
		System.out.println(analogGson.toJson(null));

		System.out.println(googleGson.toJson((byte)1));
		System.out.println(analogGson.toJson((byte)1));

		System.out.println(googleGson.toJson((short)1f));
		System.out.println(analogGson.toJson((short)1f));

		System.out.println(googleGson.toJson(1));
		System.out.println(analogGson.toJson(1));

		System.out.println(googleGson.toJson(1L));
		System.out.println(analogGson.toJson(1L));

		System.out.println(googleGson.toJson(1f));
		System.out.println(analogGson.toJson(1f));

		System.out.println(googleGson.toJson(1d));
		System.out.println(analogGson.toJson(1d));

		System.out.println(googleGson.toJson("aaa"));
		System.out.println(analogGson.toJson("aaa"));

		System.out.println(googleGson.toJson('a'));
		System.out.println(analogGson.toJson('a'));

		System.out.println(googleGson.toJson(new int[] {1, 2, 3}));
		System.out.println(analogGson.toJson(new int[] {1, 2, 3}));

		System.out.println(googleGson.toJson(List.of(1, 2 ,3)));
		System.out.println(analogGson.toJson(List.of(1, 2 ,3)));

		System.out.println(googleGson.toJson(Collections.singletonList(1)));
		System.out.println(analogGson.toJson(Collections.singletonList(1)));

	}
	
}
	
class TestClass {
	
	// Примитивы
	private boolean boolExample = false;
	
	public Integer intNumber = 456;
	
	private char charExample = 'a'; 
	
	// Строки
	public String stringExample = "Hello!";
	
	// Коллекции и массивы объектов
	public Wrapper[] objectsArray = new Wrapper[] { new Wrapper(1, "First"), new Wrapper(2, "Second") };
	
	public Collection<Wrapper> objectsCollection = new ArrayList<Wrapper>();
	
	// Коллекции и массивы примитивов
	public Boolean[] simpleArray = new Boolean[] { false, true };
	
	private int[] intArray = new int[] { 3, 4, 5 };
	
	private List<Long> longCollection = new ArrayList<Long>();
	
	public List<Double> doubleCollection = new ArrayList<Double>();
	
	Wrapper w = new Wrapper(5, "Fifth");
	
	public TestClass() {
		
		objectsCollection.add(new Wrapper(3, "Third"));
		objectsCollection.add(new Wrapper(4, "Fourth"));
		
		longCollection.add(45L);
		longCollection.add(58L);
		longCollection.add(102L);
		
		doubleCollection.add(30.1231);
		doubleCollection.add(74.78);
		
	}
	
}

class Wrapper {	
	int id;
	String name;
	
	public Wrapper(int id, String name) {
		this.id = id;
		this.name = name;
	}
}

