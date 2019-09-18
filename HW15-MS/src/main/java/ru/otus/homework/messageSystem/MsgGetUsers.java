package ru.otus.homework.messageSystem;

/**
 * Created by tully.
 */
public class MsgGetUsers extends MsgToDB {

    public MsgGetUsers(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(DBService dbService) {
        dbService.getMS().sendMessage(
        	new MsgGetUsersAnswer(
        		getTo(), 
        		getFrom(), 
        		dbService.getUsers()
       ));
    }
}
