package ru.otus.homework;

import java.util.*;

import ru.otus.homework.interfaces.*;

public abstract class MoneyKeepersAbstractFactory {

	abstract CompositeMoneyKeeper createFullCompositeDeviceWithMoney(Map<CurrencyValues, Integer> currencies);
	
	abstract MoneyKeepersManager createManagerInstance();
	
	abstract CompositeMoneyKeeper createCompositeDeviceInstance(CreationalType type);
	
	abstract OneValueMoneyKeeper createOneValueDeviceInstance(CurrencyValues currency);

}