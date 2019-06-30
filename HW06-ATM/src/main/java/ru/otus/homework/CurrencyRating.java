package ru.otus.homework;

public enum CurrencyRating {

	Ten(10),
	Fifty(50),
	OneHundred(100),
	TwoHundred(200),
	FiveHundred(500),
	OneThousand(1000),
	TwoThousand(2000),
	FiveThousand(5000);
	
	private int value;
	
	private CurrencyRating(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
