package ru.otus.homework.model;

import java.math.BigDecimal;

import ru.otus.homework.jdbc.Id;

public class Account {

	@Id
	private long no;
	
	private String type;
	
	private BigDecimal rest;
	
	public Account() {}
	
	public Account(long no, String type, BigDecimal rest) {
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
	
	public BigDecimal getRest() {
		return rest;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setRest(BigDecimal rest) {
		this.rest = rest;
	}
	
	@Override
	public String toString() {
		return "Account: no = " + no
				+ ", type = " + type
				+ ", rest = " + rest;
	}
	
}
