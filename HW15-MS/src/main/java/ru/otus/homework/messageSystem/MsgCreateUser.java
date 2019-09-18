package ru.otus.homework.messageSystem;

import ru.otus.homework.domain.User;

public class MsgCreateUser extends MsgToDB {

	private final User newUser;
	
	public MsgCreateUser(Address from, Address to, User newUser) {
		super(from, to);
		this.newUser = newUser;
	}

	@Override
	public void exec(DBService dbService) {
		dbService.saveUser(newUser);
		dbService.getMS().sendMessage(
			new MsgGetUsersAnswer(
				getTo(), 
				getFrom(), 
				dbService.getUsers()
		));
	}
}
