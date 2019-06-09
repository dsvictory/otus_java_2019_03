package ru.otus.homework;

public class ATMCell implements ConcreteRatingMoneyKeeper {

	private final int keepingRating;
	
	private static final String NO_ENOUGH_MONEY = "В ячейке недостаточно средств!";
	private static final int DEFAULT_CURRENCIES_COUNT = 0;
	
	private int currenciesCounter;
	
	public ATMCell(CurrencyRating currencyRating) {
		if (currencyRating == null) {
			throw new NullPointerException();
		}
		keepingRating = currencyRating.getValue();
		currenciesCounter = DEFAULT_CURRENCIES_COUNT;
	}
	
	@Override
	public boolean deposit(int money) {
		
		boolean result = false;
		
		if (isInputSumCorrect(money)) {
			currenciesCounter += money / keepingRating;
			result = true;
		}
		else throw new IllegalArgumentException(getInputErrorMessage());
		
		return result;
	}

	@Override
	public boolean withdraw(int money) {
		
		boolean result = false;
		
		if (isInputSumCorrect(money)) {
			if (money <= getCurrentBalance()) {
				currenciesCounter -= money / keepingRating;
				result = true;
			}
			else throw new IllegalArgumentException(NO_ENOUGH_MONEY);
		}
		else throw new IllegalArgumentException(getInputErrorMessage());
		
		
		return result;
	}

	@Override
	public int getCurrentBalance() {	
		return keepingRating * currenciesCounter;
	}

	@Override
	public int getKeepingRating() {
		return keepingRating;
	}
	
	@Override
	public int getCountCurrencies() {
		return currenciesCounter;
	}
	
	private boolean isInputSumCorrect(int money) {
		return 
			money % keepingRating == 0 
			&& money > 0;
	}

	private String getInputErrorMessage() {
		return String.format("Ячейка работает только с купюрами номиналом %d рублей!", keepingRating);
	}

	@Override
	public int getRemainingMoney() {
		int currentBalance = getCurrentBalance();
		currenciesCounter = 0;
		return currentBalance;
	}
	
}
