package ru.otus.homework;

import java.util.*;

import ru.otus.homework.interfaces.*;

/*
 
 Задействованные паттерны
 
 1. Composite - класс ATM реализует интерфейс MoneyKeeper и включает в себя список ATMCell,
 которые тоже реализуют MoneyKeeper. В итоге все операции, которые делаются с банкоматом,
 делегируются его составляющим.
 
 2. Memento - MoneyKeeper, который реализуют ATM и ATMCell, включает в себя интерйфес StateSaver,
 который предоставляет функционал для сохранения и восстановления состояния. ATM делегирует по
 паттерну компоновщик эти действия своим составляющим - ATMCell, которые сохраняют свои необходимые данные
 в экземпляре объекта внутреннего класса.
 
 3. Observer - по сути реализация компоновщика совпадает с наблюдателем, имеется список подписчиков devices
 в ATMDepartment, подписка происходит через метод addDevice(), а при вызове метода resetAllDevices()
 у все добавленных устройств вызывается метод restoreLoad(), определенный в интерфейсе. По сути такая же реализация,
 как с ActionListener. 
 
 4. Prototype - реализован через реализацию метода Cloneable в классах ATM и ATMCell, который входит в состав интерфейса MoneyKeeper.
 Создал свой интерфейс Cloneable, т.к. существующий в Java пустой, а сам метод переопределяется из Object,
 потому мы не можем оставаться в видимости интерфейсов и использовать clone(), для использования существующего интерфейса
 придется приводиться к конкретным реализациям. Один из банкоматов добавляется, как копия существующего.
 
 5. Abstract Factory + Factory Method - почти все компоненты ДЗ создаются при помощи методов DefaultMoneyKeepersFactory.
 
 6. Builder - в DefaultMoneyKeepersFactory при создании объекта CompositeMoneyKeeper (в ДЗ это банкомат)
 я просто так создал его через отдельный простенький объект построителя банкомата.
 
 7. Command - метод департамента для сброса всех банкоматов вызывается через класс комманды.
 
*/

public class ATMDepartmentDemo {

	public static void main(String[] args) {
		
		MoneyKeepersAbstractFactory factory = new DefaultMoneyKeepersFactory();
		
		MoneyKeepersManager department = factory.createManagerInstance();
		
		CompositeMoneyKeeper atm1 = factory.createFullCompositeDeviceWithMoney(
			Map.of(
				CurrencyValues.Ten, 10,
				CurrencyValues.Fifty, 2,
				CurrencyValues.OneHundred, 4,
				CurrencyValues.TwoHundred, 5,
				CurrencyValues.FiveHundred, 5,
				CurrencyValues.OneThousand, 11,
				CurrencyValues.TwoThousand, 12,
				CurrencyValues.FiveThousand, 4
			)
		);
		
		CompositeMoneyKeeper atm2 = factory.createFullCompositeDeviceWithMoney(
				Map.of(
					CurrencyValues.Ten, 1,
					CurrencyValues.Fifty, 3,
					CurrencyValues.OneHundred, 7,
					CurrencyValues.TwoHundred, 4,
					CurrencyValues.FiveHundred, 8,
					CurrencyValues.OneThousand, 9,
					CurrencyValues.TwoThousand, 2,
					CurrencyValues.FiveThousand, 20
				)
		);
		
		CompositeMoneyKeeper atm3 = (CompositeMoneyKeeper)atm1.clone();
		
		department.addDevice(atm1);
		department.addDevice(atm2);
		department.addDevice(atm3);
		
		System.out.println("Первый банкомат!");
		printInformation(atm1);
		
		System.out.println();
		
		System.out.println("Второй банкомат!");
		printInformation(atm2);
		
		System.out.println();
		
		System.out.println("Третий банкомат!");
		printInformation(atm3);
		
		System.out.println();
		
		atm1.saveState();
		atm2.saveState();
		atm3.saveState();
		
		System.out.println("Вытаскиваем все имеющиеся деньги из всех банкоматов!");
		System.out.println("Всего в остатке: " + department.getRemainingMoney());
		
		System.out.println();
		
		AbstractCommand command = new ResetCommand(department);
		command.execute();
		
		System.out.println("Банкоматы после восстановления состояния!");
		
		System.out.println("Первый банкомат!");
		printInformation(atm1);
		
		System.out.println();
		
		System.out.println("Второй банкомат!");
		printInformation(atm2);
		
		System.out.println();
		
		System.out.println("Третий банкомат!");
		printInformation(atm3);
		
	}
	
	public static void printInformation(CompositeMoneyKeeper atm) {
		for (OneValueMoneyKeeper cell : atm.getDevices()) {
			String message = String.format("Номинал %d рублей - %d штук", cell.getKeepingValue(), cell.getCountCurrencies());
			System.out.println(message);
		}
		System.out.println(String.format("Итоговая сумма: %d", atm.getCurrentBalance()));
		System.out.println();
	}
	
	
}
