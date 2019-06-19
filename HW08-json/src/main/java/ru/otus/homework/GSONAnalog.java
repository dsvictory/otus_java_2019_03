package ru.otus.homework;

import java.lang.reflect.*;
import java.util.*;
import javax.json.*;

public class GSONAnalog {
	
	private static final String WTF_MESSAGE = "(〃＞＿＜;〃)";
	
	public static final Class<?>[] INTEGERS = new Class<?>[] { byte.class, short.class, int.class, long.class };
	public static final Class<?>[] FLOATS = new Class<?>[] { float.class, double.class };
	public static final Class<Boolean> BOOLEAN = boolean.class;
	public static final Class<Character> CHAR = char.class;
	public static final Class<String> STRING = String.class;
	public static final Class<Collection> COLLECTION = Collection.class;
	
	public JsonObject toJson(Object obj) {
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			setFieldToBuilder(builder, field, obj);
		}
		
		return builder.build();
	}
	
	private JsonObjectBuilder setFieldToBuilder(JsonObjectBuilder builder, Field field, Object obj) {
		
		boolean isAccessible = field.canAccess(obj);
		if (!isAccessible) { field.setAccessible(true); }
		
		if (field.getType().isPrimitive()) {
			setPrimitiveFieldToBuilder(builder, field, obj);
		}
		else {
			setObjectFieldToBuilder(builder, field, obj);
		}
		
		if (!isAccessible) { field.setAccessible(false); }
		
		return builder;
	}
	
	private JsonObjectBuilder setPrimitiveFieldToBuilder(JsonObjectBuilder builder, Field field, Object obj) {
		
		Class<?> type = field.getType();
		String name = field.getName();
		
		try {
			if (Arrays.asList(INTEGERS).contains(type)) {
				builder.add(name, field.getLong(obj));	
			}
			if (Arrays.asList(FLOATS).contains(type)) {
				builder.add(name, field.getDouble(obj));
			}
			if (type.equals(BOOLEAN)) {
				builder.add(name, field.getBoolean(obj));
			}
			if (type.equals(CHAR)) {
				builder.add(name, field.getChar(obj));
			}
		} catch (Exception e) {
			System.out.println(WTF_MESSAGE);
			System.out.println(e.getMessage());
		}
		
		return builder;
	}
	
	private JsonObjectBuilder setObjectFieldToBuilder(JsonObjectBuilder builder, Field field, Object obj) {
		
		Class<?> type = checkType(field.getType());
		String name = field.getName();
		
		try {
			if (type.equals(STRING)) {
				builder.add(name, (String)field.get(obj));	
			} 
			else if (type.isArray()) {
				/*
				 * В этом месте упадет, если в массиве будет использоваться примитив, а не обертка,
				 * так как примитив нельзя привести к Object.
				 */
				Object[] array = (Object[])field.get(obj);

				JsonArrayBuilder arrayBuilder;
				Class<?> componentType = checkType(array.getClass().getComponentType());
				if (componentType.isPrimitive() || componentType.equals(STRING)) {
					arrayBuilder = Json.createArrayBuilder(Arrays.asList(array));
				}
				else {
					arrayBuilder = Json.createArrayBuilder();
					for (var element : array) {
						arrayBuilder.add(toJson(element));
					}
				}
				builder.add(name, arrayBuilder);
			}
			else if (isCollection(type)) {
				Collection collection = (Collection)field.get(obj);
				if (collection.isEmpty()) return builder;
				
				JsonArrayBuilder arrayBuilder;
				Class<?> componentType = checkType(collection.stream().findFirst().get().getClass());
				/*
				 * Здесь, если не привести к примитиву тип элементов коллекции,
				 * isPrimitive() не вернет true, и мы не сможем значительно более удобную
				 * перегрузку использовать Json.createArrayBuilder(Collection<?> collection),
				 * которая падает, если туда засунуть коллекцию объектов, кроме типа String.
				 * Потому используется конверсия типов оберток к примитивным аналогам.
				 */
				if (componentType.isPrimitive() || componentType.equals(STRING)) {
					arrayBuilder = Json.createArrayBuilder(collection);
				}
				else {
					arrayBuilder = Json.createArrayBuilder();
					for (var element : collection) {
						arrayBuilder.add(toJson(element));
					}
				}
				builder.add(name, arrayBuilder);
			}
			else {
				JsonObject jsonObj = toJson(field.get(obj));
				builder.add(name, jsonObj);
			}
		} catch (Exception e) {
			System.out.println(WTF_MESSAGE);
			System.out.println(e.getMessage());
		}
		
		return builder;
	}
	
	private boolean isCollection(Class<?> type) {
		return 
			Arrays.asList(type.getInterfaces()).contains(COLLECTION)
			|| type.equals(COLLECTION);
	}
	
	/**
	 * Метод используется для конверсии типов к примитивам
	 * при работе с коллекциями. Либо так, либо писать свой метод isPrimitive(),
	 * в котором держать список всех оберток.
	 * @param typeForChecking
	 * @return
	 */
	private Class<?> checkType(Class<?> typeForChecking) {
		if (typeForChecking.equals(Boolean.class)) return boolean.class;
		if (typeForChecking.equals(Byte.class)) return byte.class;
		if (typeForChecking.equals(Short.class)) return short.class;
		if (typeForChecking.equals(Integer.class)) return int.class;
		if (typeForChecking.equals(Long.class)) return long.class;
		if (typeForChecking.equals(Character.class)) return char.class;
		if (typeForChecking.equals(Float.class)) return float.class;
		if (typeForChecking.equals(Double.class)) return double.class;
		
		return typeForChecking;
	}
	
}
