package ru.otus.homework;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceManager {

	private static final boolean DEFAULT_STATE = true;
	private static final int START_VALUE = 1;
	
	private boolean isIncreaseState;
	
	private AtomicInteger value;
	
	private final int maxValue;
	
	public SequenceManager(int maxValue) {
		this.maxValue = maxValue;
		isIncreaseState = DEFAULT_STATE;
		value = new AtomicInteger(START_VALUE);
	}
	
	public synchronized int getValue() {
		int returningValue = (isIncreaseState ? value.getAndIncrement() : value.getAndDecrement());
		if (returningValue == maxValue - 1) {
			isIncreaseState = false;
		}
		if (returningValue == START_VALUE + 1) {
			isIncreaseState = true;
		}
		return returningValue;
	}
	
}
