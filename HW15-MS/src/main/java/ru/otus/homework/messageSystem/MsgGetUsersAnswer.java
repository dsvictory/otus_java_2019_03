package ru.otus.homework.messageSystem;

import java.util.*;
import ru.otus.homework.domain.User;


/**
 * Created by tully.
 */
public class MsgGetUsersAnswer extends MsgToFrontend {

	private final List<User> users;
	
    public MsgGetUsersAnswer(Address from, Address to, List<User> users) {
        super(from, to);
        this.users = users;
    }

	@Override
	public void exec(FrontendService frontendService) {
		frontendService.setUsers(users);
	}
}

