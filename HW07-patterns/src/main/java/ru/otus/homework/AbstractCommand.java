package ru.otus.homework;

import ru.otus.homework.interfaces.*;

public abstract class AbstractCommand {

	MoneyKeepersManager executor;
	
	public AbstractCommand(MoneyKeepersManager executor) {
		this.executor = executor;
	}
	
	abstract void execute();
	
}
