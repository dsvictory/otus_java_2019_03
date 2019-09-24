package ru.otus.homework.frontend.messageSystem;

import java.util.List;

import ru.otus.homework.frontend.domain.User;
import ru.otus.homework.messageSystem.Addressee;


public interface DBService extends Addressee {
    void init();

    List<User> getUsers();
    
    long saveUser(User newUser);
    
    User getUser(long id);

}