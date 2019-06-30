package ru.otus.homework;

import java.util.Arrays;

import ru.otus.homework.interfaces.*;

public class ATMCell implements OneValueMoneyKeeper {
	
	private ATMCellMemento memento;
	
	private final int keepingValue;
	private int currenciesCounter;
	
	private static final String NO_ENOUGH_MONEY = "В ячейке недостаточно средств!";
	private static final int DEFAULT_CURRENCIES_COUNT = 0;
	private static final String CTOR_ARGUMENT_ERROR_MESSAGE = "В конструктор ячейки передан null!";
	
	public ATMCell(CurrencyValues currencyRating) {
		if (currencyRating == null) {
			throw new IllegalArgumentException(CTOR_ARGUMENT_ERROR_MESSAGE);
		}
		keepingValue = currencyRating.getValue();
		currenciesCounter = DEFAULT_CURRENCIES_COUNT;
	}
	
	@Override
	public boolean deposit(CurrencyValues currency) {
		
		boolean result = false;
		
		if (isDepositSumCorrect(currency)) {
			currenciesCounter++;
			result = true;
		}
		else throw new IllegalArgumentException(getInputErrorMessage());
		
		return result;
	}

	@Override
	public boolean withdraw(int money) {
		
		boolean result = false;
		
		if (isWithdrawSumCorrect(money)) {
			if (currenciesCounter > 0) {
				currenciesCounter--;
				result = true;
			}
			else throw new IllegalArgumentException(NO_ENOUGH_MONEY);
		}
		else throw new IllegalArgumentException(getInputErrorMessage());
		
		return result;
	}

	@Override
	public int getCurrentBalance() {	
		return keepingValue * currenciesCounter;
	}
	
	private boolean isWithdrawSumCorrect(int money) {
		return 
			money != 0 
			&& money % keepingValue == 0; 
	}
	
	private boolean isDepositSumCorrect(CurrencyValues currency) {
		return currency.getValue() == keepingValue;
	}

	private String getInputErrorMessage() {
		return String.format("Ячейка работает только с купюрами номиналом %d рублей!", keepingValue);
	}

	@Override
	public int getRemainingMoney() {
		int currentBalance = getCurrentBalance();
		currenciesCounter = 0;
		return currentBalance;
	}
	
	private class ATMCellMemento {
		private final int currenciesCounter;
		
		public ATMCellMemento(int currenciesCounter) {
			this.currenciesCounter = currenciesCounter;
		}	
	}
	
	@Override
	public void saveState() {
		memento = new ATMCellMemento(currenciesCounter);
	}
	
	@Override
	public void restoreState() {
		currenciesCounter = memento.currenciesCounter;
	}

	@Override
	public int getKeepingValue() {
		return keepingValue;
	}

	@Override
	public int getCountCurrencies() {
		return currenciesCounter;
	}
	
	@Override
	public ATMCell clone() {
		CurrencyValues value = Arrays.stream(CurrencyValues.values())
								.filter(x -> x.getValue() == this.keepingValue)
								.findFirst()
								.get();
		ATMCell newObj = new ATMCell(value);
		newObj.currenciesCounter = this.currenciesCounter;
		return newObj;
	}
}
