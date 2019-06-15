package ru.otus.homework.interfaces;

public interface OneValueMoneyKeeper extends MoneyKeeper {

	/**
	 * Возвращает значение номинала,
	 * хранимое устройством
	 * @return
	 */
	int getKeepingValue();
	
	/**
	 * Возвращает количество купюр,
	 * хранимых в устройстве
	 * @return
	 */
	int getCountCurrencies();
	
}
