package ru.otus.homework.orm;

public interface ORMManager<T> {

	ORMTemplate<T> getORM();
	
}
