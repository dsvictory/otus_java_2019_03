package ru.otus.homework.interfaces;

import java.util.List;
import java.util.SortedSet;

public interface CompositeMoneyKeeper extends MoneyKeeper {

	void addDevice(OneValueMoneyKeeper device);
	
	SortedSet<OneValueMoneyKeeper> getDevices();
	
}
