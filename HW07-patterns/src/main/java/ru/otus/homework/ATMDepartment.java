package ru.otus.homework;

import java.util.*;

import ru.otus.homework.interfaces.*;

public class ATMDepartment implements MoneyKeepersManager {

	private List<CompositeMoneyKeeper> devices = new ArrayList<CompositeMoneyKeeper>();

	@Override
	public void addDevice(CompositeMoneyKeeper device) {
		devices.add(device);
	}


	@Override
	public int getRemainingMoney() {
		return devices.stream()
			.mapToInt(x -> x.getRemainingMoney())
			.sum();
	}

	@Override
	public void resetAllDevices() {
		devices.forEach(x -> x.restoreState());
	}
	
}
