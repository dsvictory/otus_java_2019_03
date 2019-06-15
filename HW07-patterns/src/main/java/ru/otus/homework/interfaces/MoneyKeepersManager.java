package ru.otus.homework.interfaces;

public interface MoneyKeepersManager {

	void addDevice(CompositeMoneyKeeper device);
	
	void resetAllDevices();
	
	int getRemainingMoney();
	
}
