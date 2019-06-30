package ru.otus.homework;

import java.util.*;

/**
 * Интерфейс, олицетворяющий устройство хранения и выдачи денег,
 * делегирующее непосредственное хранение своим составным компонентам
 * @author DSVictory
 *
 * @param <E>
 */
public interface CompositeMoneyKeeper<E> extends MoneyKeeper {

	List<E> getInternalMoneyKeepers();
	
}
