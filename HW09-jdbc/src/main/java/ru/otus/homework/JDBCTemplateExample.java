package ru.otus.homework;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

public class JDBCTemplateExample<T> implements JDBCTemplate<T> {

	private final Connection connection;
	
	private static final String ERROR_MESSAGE = "В параметры передан неподдерживаемый объект!";
	
	private static final String SAVEPOINT_NAME = "Savepoint!";
	
	private static final String UNSUPPORTED_ERROR_MESSAGE = "Неподдерживаемый тип полей класса!";
	
	public JDBCTemplateExample(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void create(T objectData) {
		
		Class<?> clazz = objectData.getClass();
		
		if (validateObject(clazz)) {
			
			Field[] fields = clazz.getDeclaredFields();
			
			String fieldsString = Arrays.stream(fields)
				.map(x -> x.getName())
				.collect(Collectors.joining(", "));
					
			String valueMarkerks = Collections.nCopies(fields.length, "?").stream()
					.collect(Collectors.joining(", "));

			String sqlCommand = String.format("insert into %1$s(%2$s) values (%3$s)",
					clazz.getSimpleName(),
					fieldsString,
					valueMarkerks);
	
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
	            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
	            for (int i = 0; i < fields.length; i++) {
	            	Class<?> fieldType = fields[i].getType();
	            	
	            	boolean isAccessible = fields[i].canAccess(objectData);
	            	if (!isAccessible) { fields[i].setAccessible(true); }
	            	
	            	// Типы для упрощения заточил сугубо под задание
	            	if (fieldType.equals(int.class)) {
	            		pst.setInt(i+1, fields[i].getInt(objectData));
	            	} 
	            	else if (fieldType.equals(long.class)) {
	            		pst.setLong(i+1, fields[i].getLong(objectData));
	            	}
	            	else if (fieldType.equals(String.class)) {
	            		pst.setString(i+1, (String)fields[i].get(objectData));
	            	}
	            	else {
	            		throw new UnsupportedOperationException(UNSUPPORTED_ERROR_MESSAGE);
	            	}
	            	
	            	if (!isAccessible) { fields[i].setAccessible(true); }
	            }
	            try {
	                int rowCount = pst.executeUpdate();
	                this.connection.commit();
	                System.out.println("Inserted rows count:" + rowCount);
	            } catch (SQLException ex) {
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
	public void update(T objectData) {
		
		Class<?> clazz = objectData.getClass();
		
		if (validateObject(clazz)) {
			
			Field idField = searchIdField(clazz);
			
			Field[] notIdFields = Arrays.stream(clazz.getDeclaredFields())
					.filter(x -> !x.equals(idField))
					.toArray(size -> new Field[size]);
			
			String sqlCommand = "update " + clazz.getSimpleName() + " set ";
			
			sqlCommand += Arrays.stream(notIdFields)
					.map(x -> x.getName() + " = ?")
					.collect(Collectors.joining(", "));
				
			sqlCommand += String.format(" where " + idField.getName() + " = ?");
			
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
	            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
	            try {
	            
	            	int i = 0;
	            	for (; i < notIdFields.length; i++) {
						
	            		Field field = notIdFields[i];
	            		Class<?> fieldType = field.getType();
	            		
						boolean isAccessible = field.canAccess(objectData);
		            	if (!isAccessible) { field.setAccessible(true); }
						
						// Типы для упрощения заточил сугубо под задание
		            	if (fieldType.equals(int.class)) {
		            		pst.setInt(i+1, field.getInt(objectData));
		            	} 
		            	else if (fieldType.equals(long.class)) {
		            		pst.setLong(i+1, field.getLong(objectData));
		            	}
		            	else if (fieldType.equals(String.class)) {
		            		pst.setString(i+1, (String)field.get(objectData));
		            	}
		            	else {
		            		throw new UnsupportedOperationException(UNSUPPORTED_ERROR_MESSAGE);
		            	}
						
						if (!isAccessible) { field.setAccessible(true); }
					}
	            	
	            	boolean isAccessible = idField.canAccess(objectData);
	            	if (!isAccessible) { idField.setAccessible(true); }
	            	
	            	pst.setLong(i+1, idField.getLong(objectData));
	            	
	            	if (!isAccessible) { idField.setAccessible(true); }
	            	
	                int rowCount = pst.executeUpdate();
	                this.connection.commit();
	                System.out.println("Updated rows count:" + rowCount);
	            } catch (SQLException ex) {
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
			
			Field[] fields = clazz.getDeclaredFields();
			
			Field idField = searchIdField(clazz);
			
			String fieldsNames = Arrays.stream(fields)
					.map(x -> x.getName())
					.collect(Collectors.joining(", "));
			
			Class<?>[] fieldsTypes = Arrays.stream(fields)
					.map(x -> x.getType())
					.toArray(size -> new Class<?>[size]);
			
			String sqlCommand = "Select " + fieldsNames 
					+ " from " + clazz.getSimpleName() 
					+ " where " + idField.getName() + " = ?";
			
			List<Object> fieldValues = new ArrayList<Object>(); 
			
			try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
				pst.setLong(1, id);
                ResultSet rs = pst.executeQuery();
                rs.next();
                
                for (Field field : fields) {
                	
                	Class<?> fieldType = field.getType();
                	String fieldName = field.getName();
                	
                	// Типы для упрощения заточил сугубо под задание
                	if (fieldType.equals(int.class)) {
	            		fieldValues.add(rs.getInt(fieldName));
	            	} 
	            	else if (fieldType.equals(long.class)) {
	            		fieldValues.add(rs.getLong(fieldName));
	            	}
	            	else if (fieldType.equals(String.class)) {
	            		fieldValues.add(rs.getString(fieldName));
	            	}
	            	else {
	            		throw new UnsupportedOperationException(UNSUPPORTED_ERROR_MESSAGE);
	            	}
                }
                
                try {
                	
                	Constructor<T> constructor = clazz.getConstructor(fieldsTypes);
                	
    				newInstance = constructor.newInstance(fieldValues.toArray());
    			} catch (Exception ex) {
    				System.out.println(ex.getMessage());
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
		
		if (searchIdField(clazz) != null) {
			result = true;
		}
		else throw new IllegalArgumentException(ERROR_MESSAGE);
		
		return result;
	}
	
	private Field searchIdField(Class<?> clazz) {
		Field result = null;
		
		if (!clazz.isPrimitive()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.getDeclaredAnnotation(Id.class) != null) {
					return field;
				}
			}
		}
		
		return result;
	}

}