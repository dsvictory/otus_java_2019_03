package ru.otus.homework;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean {
	
		private final int size;
		private final int count;
		private final int delayMilliseconds;
	
	    public Benchmark(int delayMilliseconds, int size, int count) {
	    	this.delayMilliseconds = delayMilliseconds;
	    	this.size = size;
	    	this.count = count;
	    }

	    public void run() throws InterruptedException {
	    	List<String[]> list = new ArrayList<String[]>();
	        while (true) {
	        	for (int k = 0; k < count; k++) {
	        		String[] array = new String[size];
	        		for (int i = 0; i < size; i++) {
	        			TesterGC.operationInMinute++;
	        			array[i] = new String(new char[0]);
	        		}
	        		list.add(array);
	        	}
	        	for (int j = 0; j < count / 2; j++) {
	        		list.remove(j);
	        	}
	            Thread.sleep(delayMilliseconds);
	        }  
        }
	    
	    /*
	    private String[] getArray() {
	    	String[] array = new String[arraySize];
        	for (int i = 0; i < array.length; i++) {
        		array[i] = new String(new char[0]);
        	}
        	return array;
	    }
	    */
}
