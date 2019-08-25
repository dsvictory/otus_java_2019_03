package ru.otus.homework.orm;

import java.util.List;

public interface ORMTemplate<T> {

	T getEntity(Class<T> entityClass, long id);
	
	List<T> getAll(Class<T> entityClass);
	
	long saveEntity(T entity);
	
}
