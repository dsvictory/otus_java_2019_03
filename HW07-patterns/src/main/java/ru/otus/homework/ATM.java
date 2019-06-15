package ru.otus.homework;

import java.util.*;

import ru.otus.homework.interfaces.*;

public class ATM implements CompositeMoneyKeeper {

	private final SortedSet<OneValueMoneyKeeper> cellsCollection 
		= new TreeSet<OneValueMoneyKeeper>((x,y) -> (x.getKeepingValue() - y.getKeepingValue()) * (-1));
	
	private static final String DEPOSIT_ERROR_MESSAGE = "Банкомат не поддерживает переданную банкноту!"; 
	private static final String WITHDRAW_ERROR_MESSAGE = "Запрошена недопустимая сумма!";
	private static final String NO_ENOUGH_MONEY = "В банкомате недостаточно средств!";
	private static final String NO_NECESSARY_CURRENCIES_RATINGS = "В банкомате отсутствуют необходимые номиналы для выдачи!";
	
	@Override
	public boolean deposit(CurrencyValues currency) {
		
		boolean result = false;
		
		Optional<OneValueMoneyKeeper> cell = cellsCollection.stream()
				.filter(x -> x.getKeepingValue() == currency.getValue())
				.findFirst();
			
		if (!cell.isEmpty()) {
			cell.get().deposit(currency);
			result = true;
		}
		else throw new IllegalArgumentException(DEPOSIT_ERROR_MESSAGE);
		
		return result;
	}
	
	@Override
	public boolean withdraw(int money) {		
		boolean result = false;
		
		if (isWithdrawSumCorrect(money)) {
			int sum = money;
			for (OneValueMoneyKeeper cell : cellsCollection) {
				int currentValue = cell.getKeepingValue();
				int counterCurrencies = sum / currentValue;
				if (counterCurrencies != 0) {
					int inputSum = currentValue * counterCurrencies;
					cell.withdraw(inputSum);
					sum -= inputSum;
					if (sum == 0) {
						result = true;
						break;
					}
				}
			}
		}
		
		return result;
	}

	private boolean isWithdrawSumCorrect(int money) {
		
		if (money <= 0) throw new IllegalArgumentException(WITHDRAW_ERROR_MESSAGE);
		if (money > getCurrentBalance()) throw new IllegalArgumentException(NO_ENOUGH_MONEY);
		if (!isHaveNecessaryCurrencies(money)) throw new IllegalArgumentException(NO_NECESSARY_CURRENCIES_RATINGS);
	
		return true;
	}
	
	@Override
	public int getCurrentBalance() {
		return cellsCollection.stream()
				.mapToInt(x -> x.getCurrentBalance())
				.sum();
	}
	
	@Override
	public int getRemainingMoney() {
		return cellsCollection.stream()
				.mapToInt(x -> x.getRemainingMoney())
				.sum();
	}

	private boolean isHaveNecessaryCurrencies(int money) {
		return true;
	}

	@Override
	public void saveState() {
		cellsCollection.forEach(x -> x.saveState());
	}
	
	@Override
	public void restoreState() {
		cellsCollection.forEach(x -> x.restoreState());
	}

	@Override
	public void addDevice(OneValueMoneyKeeper device) {
		cellsCollection.add(device);
	}
	
	@Override
	public ATM clone() {
		ATM newObj = new ATM();
		for (OneValueMoneyKeeper cell : cellsCollection) {
			newObj.addDevice((OneValueMoneyKeeper)cell.clone());
		}
		return newObj;
	}

	@Override
	public SortedSet<OneValueMoneyKeeper> getDevices() {
		return cellsCollection;
	}

}
