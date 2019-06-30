package ru.otus.homework.jdbc;

import ru.otus.homework.reflect.*;
import java.lang.reflect.*;
import java.sql.*;
import ru.otus.homework.reflect.ClassMetaData;

public class JDBCTemplateImp<T> implements JDBCTemplate<T> {

	private ClassMetaData reflectionParts;
	
	private final Connection connection;
	
	private static final String ERROR_MESSAGE = "В параметры передан неподдерживаемый объект!";
	private static final String SAVEPOINT_NAME = "Default savepoint";
	
	public JDBCTemplateImp(Class<T> type, Connection connection) {
		this.connection = connection;
		if (!validateObject(type)) {
			throw new IllegalArgumentException(ERROR_MESSAGE);
		}
	}
	
	@Override
	public void create(T objectData) {
		String sqlCommand = reflectionParts.getInsertCommand();
		try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
            ReflectionHelper.setFieldsValuesToCommand(pst, reflectionParts.getFields(), objectData);
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
	
	@Override
	public void update(T objectData) {
		String sqlCommand = reflectionParts.getUpdateCommand();
		try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
            Savepoint savePoint = this.connection.setSavepoint(SAVEPOINT_NAME);
        
        	Field[] notIdFields = reflectionParts.getNoFields();
        	ReflectionHelper.setFieldsValuesToCommand(pst, notIdFields, objectData);
     
        	Field idField = reflectionParts.getIdField();
        	
        	boolean isAccessible = idField.canAccess(objectData);
        	if (!isAccessible) { idField.setAccessible(true); }
        	
        	pst.setLong(notIdFields.length + 1, idField.getLong(objectData));
        	
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
	
	@Override
	public T load(long id, Class<T> clazz) {
		
		T newInstance = null;
		
		String sqlCommand = reflectionParts.getSelectCommand();
		try (PreparedStatement pst = connection.prepareStatement(sqlCommand)) {
			
			pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            
            newInstance = clazz.getConstructor().newInstance();
            
            Field[] fields = reflectionParts.getFields();
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
		
		return newInstance;
	}
	
	private boolean validateObject(Class<?> clazz) {
		boolean result = false;
		if (ReflectionHelper.getIdField(clazz) != null) {
			reflectionParts = new ClassMetaData(clazz);
			result = true;
		}
		return result;
	}
	
}