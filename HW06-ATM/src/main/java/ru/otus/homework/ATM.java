package ru.otus.homework;

import java.util.*;

public class ATM implements CompositeMoneyKeeper<ConcreteRatingMoneyKeeper> {

	public final List<ConcreteRatingMoneyKeeper> cellsCollection;
	
	private static final String INPUT_ERROR_MESSAGE = "Недопустимое значение!"; 
	private static final String NO_ENOUGH_MONEY = "В банкомате недостаточно средств!";
	private static final String NO_NECESSARY_CURRENCIES_RATINGS = "В банкомате отсутствуют необходимые номиналы для выдачи!";
	private static final int MIN_INPUT_SUM = 10;
	
	public ATM() {
		cellsCollection = new ArrayList<ConcreteRatingMoneyKeeper>();
		SetCellsCollection();
		cellsCollection.sort((x,y) -> (x.getKeepingRating() - y.getKeepingRating()) * (-1));
	}
	
	@Override
	public boolean deposit(int money) {
		
		boolean result = false;
		
		if (isInputSumCorrect(money)) {
			int inputMoney = money;
			for (int i = 0; i < cellsCollection.size() || inputMoney != 0 ; i++) {
				ConcreteRatingMoneyKeeper cell = cellsCollection.get(i);
				int addingMoneyToCurrentCell = inputMoney / cell.getKeepingRating() * cell.getKeepingRating();
				if (addingMoneyToCurrentCell != 0) {
					cell.deposit(addingMoneyToCurrentCell);
					inputMoney -= addingMoneyToCurrentCell;
				}
			}
			result = true;
		}
		else throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
		
		return result;
	}

	@Override
	public boolean withdraw(int money) {
		
		boolean result = false;
		
		if (isInputSumCorrect(money)) {
			if (money <= getCurrentBalance()) {
				if (isHaveNecessaryCurrencies(money)) {
					int outputMoney = money;
					for (int i = 0; i < cellsCollection.size() || outputMoney != 0 ; i++) {
						ConcreteRatingMoneyKeeper cell = cellsCollection.get(i);
						int removingMoneyFromCurrentCell = outputMoney / cell.getKeepingRating() * cell.getKeepingRating();
						if (removingMoneyFromCurrentCell != 0) {
							cell.withdraw(removingMoneyFromCurrentCell);
							outputMoney -= removingMoneyFromCurrentCell;
						}
					}
					result = true;
				} else throw new IllegalArgumentException(NO_NECESSARY_CURRENCIES_RATINGS);
			}
			else throw new IllegalArgumentException(NO_ENOUGH_MONEY);
		}
		else throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
		
		return result;
	}

	@Override
	public int getCurrentBalance() {
		return cellsCollection.stream()
				.mapToInt(x -> x.getCurrentBalance())
				.sum();
	}

	@Override
	public List<ConcreteRatingMoneyKeeper> getInternalMoneyKeepers() {
		return cellsCollection;
	}
	
	@Override
	public int getRemainingMoney() {
		return cellsCollection.stream().mapToInt(x -> x.getRemainingMoney()).sum();
	}
	
	private void SetCellsCollection() {
		cellsCollection.add(new ATMCell(CurrencyRating.Ten));
		cellsCollection.add(new ATMCell(CurrencyRating.Fifty));
		cellsCollection.add(new ATMCell(CurrencyRating.OneHundred));
		cellsCollection.add(new ATMCell(CurrencyRating.TwoHundred));
		cellsCollection.add(new ATMCell(CurrencyRating.FiveHundred));
		cellsCollection.add(new ATMCell(CurrencyRating.OneThousand));
		cellsCollection.add(new ATMCell(CurrencyRating.TwoThousand));
		cellsCollection.add(new ATMCell(CurrencyRating.FiveThousand));
	}
	
	private boolean isInputSumCorrect(int money) {
		return 
			money > MIN_INPUT_SUM
			&& money % MIN_INPUT_SUM == 0;
	}


	private boolean isHaveNecessaryCurrencies(int money) {
		
		return true;
	}
}
