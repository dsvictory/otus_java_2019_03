package ru.otus.homework.messages;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.Message;
import com.google.gson.Gson;

public class MsgCreateUser extends Message {

	private final User newUser;
	
	public MsgCreateUser(Address from, Address to, User newUser) {
		super(MsgCreateUser.class, from, to);
		this.newUser = newUser;
	}
	
	public String getUserJson() {
		return new Gson().toJson(newUser);
	}
}