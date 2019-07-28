package ru.otus.homework.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String street;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Account account;
	
	public Address() {}
	
	public Address(String street, Account account) {
		this.street = street;
		this.account = account;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void seStreet(String street) {
		this.street = street;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Address: street = " + street;
	}
	
}
