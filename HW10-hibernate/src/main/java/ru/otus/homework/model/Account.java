package ru.otus.homework.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String type;
	
	@Column
	private BigDecimal rest;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> address = new ArrayList<>();
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public BigDecimal getRest() {
		return rest;
	}
	
	public void setRest(BigDecimal rest) {
		this.rest = rest;
	}
	
	public List<Address> getAddress() {
		return address;
	}
	
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Account: id = " + id
				+ ", type = " + type
				+ ", rest = " + rest;
	}
	
}
