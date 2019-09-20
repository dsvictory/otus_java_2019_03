package ru.otus.homework.messageSystem;

import java.util.List;

import ru.otus.homework.domain.User;


public interface DBService extends Addressee {
    void init();

    List<User> getUsers();
    
    long saveUser(User newUser);
    
    User getUser(long id);

}