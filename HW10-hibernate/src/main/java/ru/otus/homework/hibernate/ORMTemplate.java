package ru.otus.homework.hibernate;

public interface ORMTemplate<T> {

	T getEntity(Class<T> entityClass, long id);
	
	boolean saveEntity(T entity);
	
}
