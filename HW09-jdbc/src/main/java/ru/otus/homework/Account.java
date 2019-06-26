package ru.otus.homework;

public class Account {

	@Id
	private final long no;
	
	private String type;
	
	private int rest;
	
	public Account(long no, String type, int rest) {
		this.no = no;
		this.type = type;
		this.rest = rest;
	}
	
	public long getNo() {
		return no;
	}
	
	public String getType() {
		return type;
	}
	
	public int getRest() {
		return rest;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setRest(int rest) {
		this.rest = rest;
	}
	
	@Override
	public String toString() {
		return "Account: no = " + no
				+ ", type = " + type
				+ ", rest = " + rest;
	}
	
}
