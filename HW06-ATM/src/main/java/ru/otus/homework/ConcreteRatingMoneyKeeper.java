package ru.otus.homework;

/**
 * Интерфейс, олицетворяющий объект, хранящий и выдающий наличные,
 * который имеет возможность хранить только какой-либо конкретный номинал
 * @author DSVictory
 *
 */
public interface ConcreteRatingMoneyKeeper extends MoneyKeeper {

	/**
	 * Получение номинала, который может храниться в объекте
	 * @return
	 */
	int getKeepingRating();
	
	/**
	 * Получение количества купюр, хранящихся в объекте
	 * @return
	 */
	int getCountCurrencies();
	
}
