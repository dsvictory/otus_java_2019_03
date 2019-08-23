package ru.otus.homework.services;

public interface ORMManager<T> {

	ORMTemplate<T> getORM();
	
}
