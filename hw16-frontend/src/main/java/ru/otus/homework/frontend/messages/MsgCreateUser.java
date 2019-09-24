package ru.otus.homework.frontend.messages;

import ru.otus.homework.frontend.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messages.Message;
import com.google.gson.Gson;

public class MsgCreateUser extends Message {

	private final User newUser;
	
	public MsgCreateUser(Address from, Address to, User newUser) {
		super(MsgCreateUser.class, from, to);
		this.newUser = newUser;
	}

	@Override
	public String toString() {
		return new Gson().toJson(newUser);
	}
}
