package ru.otus.homework.reflect;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;
import java.util.stream.Collectors;

import ru.otus.homework.jdbc.Id;

public final class ReflectionHelper {

	public static Field[] getFields(Class<?> clazz) {
		return clazz.getDeclaredFields();
	}
	
	public static String getFieldsNames(Class<?> clazz) {
		return Arrays.stream(getFields(clazz))
				.map(x -> x.getName())
				.collect(Collectors.joining(", "));
	}
	
	public static Field getIdField(Class<?> clazz) {
		if (!clazz.isPrimitive()) {
			Field[] fields = getFields(clazz);
			for (Field field : fields) {
				if (field.getDeclaredAnnotation(Id.class) != null) {
					return field;
				}
			}
		}
		return null;
	}
	
	public static Field[] getNotIdFields(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredFields())
			.filter(x -> !x.equals(getIdField(clazz)))
			.toArray(size -> new Field[size]);
	}
	
	public static Constructor<?> getDefaultConstructor(Class<?> clazz) {
		Constructor<?> constructor = null;
		try {
			constructor = clazz.getConstructor();
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return constructor;
	}
	
	public static void setFieldsValuesToCommand(PreparedStatement pst, Field[] fields, Object obj) {
		try {
			for (int i = 0; i < fields.length; i++) {
	        	boolean isAccessible = fields[i].canAccess(obj);
	        	if (!isAccessible) { fields[i].setAccessible(true); }
	        	
	        	Object value = fields[i].get(obj);
	        	pst.setObject(i+1, value);
	        	
	        	if (!isAccessible) { fields[i].setAccessible(true); }
	        }
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
