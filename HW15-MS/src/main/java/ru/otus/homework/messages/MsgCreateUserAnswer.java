package ru.otus.homework.messages;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Address;
import ru.otus.homework.messageSystem.FrontendService;

public class MsgCreateUserAnswer extends MsgToFrontend {
	
	private final User newUser;
	
    public MsgCreateUserAnswer(Address from, Address to, User newUser) {
        super(from, to);
        this.newUser = newUser;
    }

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.addUser(newUser);
	}
}

