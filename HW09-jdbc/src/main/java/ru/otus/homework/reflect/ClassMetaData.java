package ru.otus.homework.reflect;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class ClassMetaData {

	private String simpleName;
	
	private Field[] fields;
	private String fieldsNames;
	
	private Field idField;
	private String idFieldName;
	private Field[] notIdFields;
	
	private Constructor<?> defaultConstructor;
	
	private String insertCommand;
	private String updateCommand;
	private String selectCommand;
	
	public ClassMetaData(Class<?> clazz) {
		
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
	
	public String getSimpleName() {
		return simpleName;
	}
	
	public Field[] getFields() {
		return fields;
	}
	
	public Field getIdField() {
		return idField;
	}
	
	public String getIdString() {
		return idFieldName;
	}
	
	public Field[] getNoFields() {
		return notIdFields;
	}
	
	public Constructor<?> getDefaultConstructor() {
		return defaultConstructor;
	}
	
	public String getInsertCommand() {
		return insertCommand;
	}
	
	public String getUpdateCommand() {
		return updateCommand;
	}
	
	public String getSelectCommand() {
		return selectCommand;
	}
	
}
