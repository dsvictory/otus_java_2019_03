package ru.otus.homework;

import java.util.*;

/**
 * Сконцентрировался на минимальной работающей версии коллекции,
 * отвечающей требованиям ДЗ
 * 
 * @author DSVictory
 *
 * @param <E>
 */
public class DIYarrayList<E> implements List<E> {
	
	/**
	 * Для упрощения расчетов размера массива (и чтобы пока не слизывать существующие алгоритмы :) ),
	 * просто заранее делаю множитель для резервирования на будущее,
	 * т.е. при добавлении 10 элементов массив станет размером 20,
	 * затем при добавлении 50 элементов И выходе за границы уже существующего массива
	 * будет добавлено 100 ячеек к массиву
	 */
	private static final int DEFAULT_CAPACITY_MULTIPLIER = 2;
	
	/**
	 * Пустой массив для выставления пустого значения коллекции
	 */
	private static final Object[] EMPTY_ARRAY = {};
	
	/**
	 * Массив для хранения элементов коллекции
	 */
	private Object[] dataArray;
	
	/**
	 * Число элементов в коллекции
	 */
	private int size;
	
	/**
	 * Дефолтный конструктор,
	 * выставляем пустой массив
	 */
	public DIYarrayList() {
		this.dataArray = EMPTY_ARRAY;
	}
	
	/**
	 * Конструктор с передачей инициализирующей коллекции,
	 * забрал существующую реализацию с заплаткой от бага
	 * @param collection
	 */
	public DIYarrayList(Collection<? extends E> collection) {
		this.dataArray = collection.toArray();
		this.size = this.dataArray.length;
        if (this.size != 0) {
            // defend against c.toArray (incorrectly) not returning Object[]
            // (see e.g. https://bugs.openjdk.java.net/browse/JDK-6260652)
            if (this.dataArray.getClass() != Object[].class) {
            	this.dataArray = Arrays.copyOf(this.dataArray, this.size, Object[].class);
            }
        } else {
            // replace with empty array.
            this.dataArray = EMPTY_ARRAY;
        }
	}
	
	/**
	 * Геттер для кол-ва элементов в коллекции
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * А не пустая ли у нас коллекция?!!!
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<E> iterator() {
		return new DIYarrayListIterator();
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(this.dataArray, size);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Добавляет коллекцию к текущей
	 */
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		
		//Проверяем на пустоту пришедшую коллекцию
		Object[] addingCollectionArray = collection.toArray();
        int numNewElements = addingCollectionArray.length;
        if (numNewElements == 0) {
            return false;
        }
        
        Object[] currentCollectionArray = this.dataArray;
        int currentCollectionSize = this.size;
        //Проверяем, хватит ли свободного места в массиве
        if (numNewElements > currentCollectionArray.length - currentCollectionSize) {
        	//Т.к. места не хватит - наращиваем массив
        	currentCollectionArray = growArrayCapacity(currentCollectionSize + numNewElements);
        }
        //Запихиваем коллекцию в текущую
        this.dataArray = concatArrays(currentCollectionArray, addingCollectionArray, currentCollectionSize);
        size = currentCollectionSize + numNewElements;
        
        return true;
	}
	
	/**	
	 * Метод увеличивает емкость массива, используемого для хранения эл-тов коллекции
	 */
	private Object[] growArrayCapacity(int minCapacity) {
		return Arrays.copyOf(dataArray, newCapacity(minCapacity));
	}
	
	/**
	 * Этот метод инкапсулирует любую логику создания запаса
	 * для будущих элементов в коллекции,
	 * пока просто поставил множитель, не стал забирать логику из библиотеки
	 * с проверкой верхней границы коллекции
	 * 
	 * @param minCapacity
	 * @return
	 */
	private int newCapacity(int minCapacity) {
		return minCapacity * DEFAULT_CAPACITY_MULTIPLIER;
	}
	
	/**
	 * Вставляет элементы массива в корневой массив коллекции,
	 * в позиции с индекса startIndex
	 * @param first
	 * @param second
	 * @return
	 */
	private Object[] concatArrays(Object[] currentArray, Object[] arrayForInsert, int startIndex) {

		int secondLength = arrayForInsert.length;
		Object[] newArray = currentArray;
		
		//Переносим элементы второго массива в первый массив
		int indexSecondArray = 0;
		for (int i = startIndex; i < startIndex + secondLength; i++) {
			newArray[i] = arrayForInsert[indexSecondArray];
			indexSecondArray++;
		}
		
		return newArray;
		
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E get(int index) {
		return (E)this.dataArray[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	public E set(int index, E element) {
        E oldValue = (E)dataArray[index];
        dataArray[index] = element;
        return oldValue;
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		return new DIYarrayListListIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
	
	private class DIYarrayListIterator implements Iterator<E> {
		
		int cursor = 0;
		int lastRet = -1;
		
		@Override
		public boolean hasNext() {
			return cursor != size;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public E next() {
			int i = cursor;
            Object[] currentCollectionArray = DIYarrayList.this.dataArray;
            cursor = i + 1;
            return (E)currentCollectionArray[lastRet = i];
		}
		
	}
	
	private class DIYarrayListListIterator extends DIYarrayListIterator
		implements ListIterator<E> {

		@Override
		public boolean hasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public E previous() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void set(E e) {
            try {
                DIYarrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
			
		}

		@Override
		public void add(E e) {
			// TODO Auto-generated method stub
			
		}		
	}
	
}