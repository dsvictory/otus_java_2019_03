package ru.otus.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.json.*;


/**
 * ОСНОВНАЯ БЕДА
 * 
 * Из-за различных классов boolean.class и Boolean.class и аналогично с другими примитивами
 * во время игр с рефлексией возникает куча проблем.
 * Для корректной работы программы необходимо, чтобы обычные примитивные поля записывались
 * именно как примитивы (int, boolean, char и т.д.),
 * но, когда мы работаем с коллекциями, необходимо указывать именно тип обертки.
 * 
 * Иначе будет исключение, и эти поля не попадут в итоговый JSON.
 * 
 * @author DSVictory
 *
 */
public class JSONSerializeDemo {

	public static void main(String[] args) {
		
		GSONAnalog gson = new GSONAnalog();
		
		TestClass a = new TestClass();
		
		JsonObject json = gson.toJson(a);
		
		System.out.println(json);
		
	}

}

class TestClass {
	
	// Примитивы
	private boolean boolExample = false;
	
	private int intNumber = 45;
	
	private double doubleNumber = 450.78; 
	
	// Строки
	public String stringExample = "Hello!";
	
	// Коллекции и массивы объектов
	public Wrapper[] objectsArray = new Wrapper[] { new Wrapper(1, "First"), new Wrapper(2, "Second") };
	
	public Collection<Wrapper> objectsCollection = new ArrayList<Wrapper>();
	
	// Коллекции и массивы примитивов
	public Boolean[] simpleArray = new Boolean[] { false, true };
	
	private List<Long> simpleCollection = new ArrayList<Long>();
	
	Wrapper w = new Wrapper(5, "Fifth");
	
	public TestClass() {
		
		objectsCollection.add(new Wrapper(3, "Third"));
		objectsCollection.add(new Wrapper(4, "Fourth"));
		
		simpleCollection.add(45L);
		simpleCollection.add(58L);
		simpleCollection.add(102L);
		
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

