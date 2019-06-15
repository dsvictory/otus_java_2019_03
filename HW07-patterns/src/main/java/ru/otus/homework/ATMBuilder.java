package ru.otus.homework;

import java.util.SortedSet;
import java.util.TreeSet;

import ru.otus.homework.interfaces.OneValueMoneyKeeper;

public class ATMBuilder {

	private SortedSet<OneValueMoneyKeeper> cellsCollection 
	= new TreeSet<OneValueMoneyKeeper>((x,y) -> (x.getKeepingValue() - y.getKeepingValue()) * (-1));
	
	public ATMBuilder setCells(OneValueMoneyKeeper cell) {
		cellsCollection.add(cell);
		return this;
	}
	
	public ATM build() {
		ATM atm = new ATM();
		cellsCollection.forEach(x -> atm.addDevice(x));
		return atm;
	}
	
}
