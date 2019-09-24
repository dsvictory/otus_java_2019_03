package ru.otus.homework.messages;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.DBService;

public class MsgCreateUser extends MsgToDB {

	private final User newUser;
	
	public MsgCreateUser(Address from, Address to, User newUser) {
		super(from, to);
		this.newUser = newUser;
	}

	@Override
	public void exec(DBService dbService) {
		long savedUserId = dbService.saveUser(newUser);
		dbService.getMS().sendMessage(
			new MsgCreateUserAnswer(
				getTo(), 
				getFrom(), 
				dbService.getUser(savedUserId)
		));
	}
}
