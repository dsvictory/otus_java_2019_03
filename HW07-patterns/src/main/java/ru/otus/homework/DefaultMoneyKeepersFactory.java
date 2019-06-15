package ru.otus.homework;

import java.util.*;

import ru.otus.homework.interfaces.*;

public class DefaultMoneyKeepersFactory extends MoneyKeepersAbstractFactory {

	@Override
	MoneyKeepersManager createManagerInstance() {
		return new ATMDepartment();
	}

	@Override
	CompositeMoneyKeeper createCompositeDeviceInstance(CreationalType type) {
		
		ATMBuilder builder = new ATMBuilder();
		
		if (type == CreationalType.Full) {
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.Ten));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.Fifty));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.OneHundred));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.TwoHundred));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.FiveHundred));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.OneThousand));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.TwoThousand));
			builder.setCells(createOneValueDeviceInstance(CurrencyValues.FiveThousand));
		}
		
		return builder.build();
	}

	@Override
	OneValueMoneyKeeper createOneValueDeviceInstance(CurrencyValues currency) {
		return new ATMCell(currency);
	}

	@Override
	CompositeMoneyKeeper createFullCompositeDeviceWithMoney(Map<CurrencyValues, Integer> currencies) {
		CompositeMoneyKeeper atm = createCompositeDeviceInstance(CreationalType.Full);
		
		currencies.forEach((x,y) -> {
			for (int i = 0; i < y; i++) {
				atm.deposit(x);
			}
		});
		
		return atm;
	}
	
}
