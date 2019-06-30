package ru.otus.homework;

/*
 
 Не реализован только алгоритм проверки, достаточное ли количество в банкомате именно необходимых номиналов
 для выдачи денег, так как только лишь достаточное количество средств в банкомате не говорит о том, что
 средства могут быть выданы. Как пример, попытка получить из банкомата 7 тысяч, когда там лежит только
 2 купюры по 5 тысяч. Метод isHaveNecessaryCurrencies(), осуществляющий такую проверку пока не реализован,
 так как в ДЗ указано, что задача не на алгоритмы.
 
 Потому в тестах следует вытаскивать из банкомата только те суммы, которые можно составить из лежащих в нем банкнот.
 
*/

public class ATMdemo {

	public static void main(String[] args) {
		
		CompositeMoneyKeeper<ConcreteRatingMoneyKeeper> atm = new ATM();
		
		printInformation(atm);
		
		atm.deposit(43760);
		
		printInformation(atm);
		
		atm.withdraw(3050);

		printInformation(atm);
		
		int remains = atm.getRemainingMoney();
		System.out.println("Выдан остаток: " + remains + " рублей");
		
		printInformation(atm);
		
	}
	
	public static void printInformation(CompositeMoneyKeeper<ConcreteRatingMoneyKeeper> atm) {
		for (ConcreteRatingMoneyKeeper cell : atm.getInternalMoneyKeepers()) {
			String message = String.format("Номинал %d рублей - %d штук", cell.getKeepingRating(), cell.getCountCurrencies());
			System.out.println(message);
		}
		System.out.println(String.format("Итоговая сумма: %d", atm.getCurrentBalance()));
		System.out.println();
	}

}
