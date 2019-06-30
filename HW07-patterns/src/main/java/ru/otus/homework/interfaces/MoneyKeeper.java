package ru.otus.homework.interfaces;

import ru.otus.homework.CurrencyValues;

public interface MoneyKeeper extends StateSaver, Cloneable {
	
	/**
	 * Внесение наличных
	 * @param money
	 * @return
	 */
	boolean deposit(CurrencyValues currency);
	
	/**
	 * Снятие/извлечение наличных
	 * @param money
	 * @return
	 */
	boolean withdraw(int money);
	
	/**
	 * Получить текущий баланс
	 * @return
	 */
	int getCurrentBalance();
	
	
	/**
	 * Снятие остатка средств
	 * @return
	 */
	int getRemainingMoney();
	
}
