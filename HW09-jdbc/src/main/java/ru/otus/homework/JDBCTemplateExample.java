package ru.otus.homework;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class JDBCTemplateExample<T> implements JDBCTemplate<T> {

	private ClassReflectionParts reflectionParts;
	
	private final Connection connection;
	
	private static final String ERROR_MESSAGE = "В параметры передан неподдерживаемый объект!";
	
	private static final String SAVEPOINT_NAME = "Default savepoint";
	
	public JDBCTemplateExample(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void create(T objectData) {
		
		Class<?> clazz = objectData.getClass();
		
		if (validateObject(clazz)) {
			
			Field[] fields = reflectionParts.fields;

			String sqlCommand = reflectionParts.insertCommand;
	
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
	            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
	            
	            for (int i = 0; i < fields.length; i++) {
	            	boolean isAccessible = fields[i].canAccess(objectData);
	            	if (!isAccessible) { fields[i].setAccessible(true); }
	            	
	            	Object value = fields[i].get(objectData);
	            	pst.setObject(i+1, value);
	            	
	            	if (!isAccessible) { fields[i].setAccessible(true); }
	            }
	            
	            try {
	                int rowCount = pst.executeUpdate();
	                this.connection.commit();
	                System.out.println("Inserted rows count:" + rowCount);
	            } 
	            catch (SQLException ex) {
	                this.connection.rollback(savePoint);
	                System.out.println(ex.getMessage());
	            }
	            
	        }
			catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			
		}
	}
	
	@Override
	public void update(T objectData) {
		
		Class<?> clazz = objectData.getClass();
		
		if (validateObject(clazz)) {
			
			Field idField = reflectionParts.idField;
			Field[] notIdFields = reflectionParts.notIdFields;
			
			String sqlCommand = reflectionParts.updateCommand;
			
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
	            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
	          
	            	int i = 0;
	            	for (; i < notIdFields.length; i++) {
						
	            		Field field = notIdFields[i];
	       
						boolean isAccessible = field.canAccess(objectData);
		            	if (!isAccessible) { field.setAccessible(true); }
						
		            	Object value = field.get(objectData);
		            	pst.setObject(i+1, value);
						
						if (!isAccessible) { field.setAccessible(true); }
					}
	            	
	            	boolean isAccessible = idField.canAccess(objectData);
	            	if (!isAccessible) { idField.setAccessible(true); }
	            	
	            	pst.setLong(i+1, idField.getLong(objectData));
	            	
	            	if (!isAccessible) { idField.setAccessible(true); }
	            	
            	try {
	                int rowCount = pst.executeUpdate();
	                this.connection.commit();
	                System.out.println("Updated rows count:" + rowCount);
	            } 
            	catch (SQLException ex) {
	                this.connection.rollback(savePoint);
	                System.out.println(ex.getMessage());
	            }
            	
	        }
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	@Override
	public T load(long id, Class<T> clazz) {
		
		T newInstance = null;
		
		if (validateObject(clazz)) {

			String sqlCommand = reflectionParts.selectCommand;
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
				
				pst.setLong(1, id);
                ResultSet rs = pst.executeQuery();
                rs.next();
                
                newInstance = clazz.getConstructor().newInstance();
                
                Field[] fields = reflectionParts.fields;
                for (Field field : fields) {
                	
                	boolean isAccessible = field.canAccess(newInstance);
	            	if (!isAccessible) { field.setAccessible(true); }

                	String fieldName = field.getName();
                	Object value = rs.getObject(fieldName);
                	
                	field.set(newInstance, value);
                	
                	if (!isAccessible) { field.setAccessible(true); }

                }
	        }
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		
		}
		return newInstance;
	}
	
	private boolean validateObject(Class<?> clazz) {
		boolean result = false;
		
		if (ReflectionHelper.getIdField(clazz) != null) {
			saveReflectionParts(clazz);
			result = true;
		}
		else throw new IllegalArgumentException(ERROR_MESSAGE);
		
		return result;
	}
	
	private void saveReflectionParts(Class<?> clazz) {
		if (reflectionParts == null) {
			reflectionParts = new ClassReflectionParts(clazz);
		}
	}
	
}