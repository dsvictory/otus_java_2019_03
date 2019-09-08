package ru.otus.homework;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoJMM {
    
	private static final int MAX_VALUE = 10;
	
	private volatile int currentValue;
	
	private volatile AtomicInteger threadsCount = new AtomicInteger(0);
    
    private SequenceManager seqManager = new SequenceManager(MAX_VALUE);

    public static void main(String[] args) throws InterruptedException {
    	DemoJMM demo = new DemoJMM();
        demo.go();
    }
    
    private void go() throws InterruptedException {
        Thread thread1 = new Thread(this::inc);
        thread1.setName("Thread1");
        Thread thread2 = new Thread(this::inc);
        thread2.setName("Thread2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private void inc() {
        while (true) {
        	synchronized(this) {
        		if (threadsCount.get() != 0) {
        			System.out.println(Thread.currentThread().getName() + " - " + currentValue);
        			threadsCount.set(0);
        			notifyAll();
    			}
        		else {
        			currentValue = seqManager.getValue();
        			System.out.println(Thread.currentThread().getName() + " - " + currentValue);
        			threadsCount.getAndIncrement();
        			try {
						wait();
					} catch (InterruptedException e) {}
        		}
			}
        }
    }
    
}