package ru.otus.homework.jdbc;

public interface JDBCTemplate<T> {

	void create(T objectData);
	
	void update(T objectData);
	
	T load(long id, Class<T> clazz);
	
}
