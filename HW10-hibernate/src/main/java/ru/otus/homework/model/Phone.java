package ru.otus.homework.model;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column
	private String number;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
	
	public Phone() {}
	
	public Phone(String number, User user) {
        this.number = number;
        this.user = user;
    }

	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Phone: number = " + number;
	}

}
