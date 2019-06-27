package ru.otus.homework;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class ClassReflectionParts {

	public final String simpleName;
	
	public final Field[] fields;
	public final String fieldsNames;
	
	public final Field idField;
	public final String idFieldName;
	public final Field[] notIdFields;
	
	public final Constructor<?> defaultConstructor;
	
	public final String insertCommand;
	public final String updateCommand;
	public final String selectCommand;
	
	public ClassReflectionParts(Class<?> clazz) {
		
		simpleName = clazz.getSimpleName();
		fields = ReflectionHelper.getFields(clazz);
		fieldsNames = ReflectionHelper.getFieldsNames(clazz);
		idField = ReflectionHelper.getIdField(clazz);
		idFieldName = idField.getName();
		notIdFields = ReflectionHelper.getNotIdFields(clazz);

		defaultConstructor = ReflectionHelper.getDefaultConstructor(clazz);
		
		insertCommand = getInsertSQLCommand();
		updateCommand = getUpdateSQLCommand();
		selectCommand = getSelectSQLCommand();
	}
	
	private String getInsertSQLCommand() {
		
		String valuesMarkerks = Collections.nCopies(fields.length, "?").stream()
				.collect(Collectors.joining(", "));
		
		return "insert into " + simpleName
				+ "(" + fieldsNames + ")"
				+ " values (" + valuesMarkerks + ")";
	}
	
	private String getUpdateSQLCommand() {
		
		String sqlCommand = "update " + simpleName + " set ";
		
		sqlCommand += Arrays.stream(notIdFields)
				.map(x -> x.getName() + " = ?")
				.collect(Collectors.joining(", "));
			
		sqlCommand += String.format(" where " + idFieldName + " = ?");
		
		return sqlCommand;
	}
	
	private String getSelectSQLCommand() {
		return "Select " + fieldsNames
				+ " from " + simpleName
				+ " where " + idFieldName + " = ?";
	}
	
}
