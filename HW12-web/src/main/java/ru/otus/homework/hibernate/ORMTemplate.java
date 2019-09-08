package ru.otus.homework.hibernate;

import java.util.List;

public interface ORMTemplate<T> {

	T getEntity(Class<T> entityClass, long id);
	
	List<T> getAll(Class<T> entityClass);
	
	boolean saveEntity(T entity);
	
}
