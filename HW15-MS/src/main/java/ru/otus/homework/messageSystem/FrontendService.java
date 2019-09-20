package ru.otus.homework.messageSystem;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Addressee;

public interface FrontendService extends Addressee {
	
    void init();
    
    void addUser(User newUser);
    
}

