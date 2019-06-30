package ru.otus.homework;

public enum CreationalType {

	Empty("Создается многокомпонентное устройство без заполнения внутренними компонентами"),
	Full("Создается многокомпонентное устройства с полной внутренней комплектацией");
	
	public final String description;
	
	private CreationalType(String desc) {
		this.description = desc;
	}
	
}
