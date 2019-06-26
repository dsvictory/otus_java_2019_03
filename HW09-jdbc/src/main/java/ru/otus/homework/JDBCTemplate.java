package ru.otus.homework;

public interface JDBCTemplate<T> {

	void create(T objectData);
	
	void update(T objectData);
	
	T load(long id, Class<T> clazz);
	
}
