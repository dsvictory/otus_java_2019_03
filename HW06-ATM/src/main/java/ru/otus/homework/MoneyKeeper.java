package ru.otus.homework;

public interface MoneyKeeper {

	/**
	 * Внесение наличных
	 * @param money
	 * @return
	 */
	boolean deposit(int money);
	
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
