package ru.otus.homework;

import ru.otus.homework.interfaces.*;

public class ResetCommand extends AbstractCommand {

	public ResetCommand(MoneyKeepersManager executor) {
		super(executor);
	}

	@Override
	void execute() {
		executor.resetAllDevices();
	}

}
