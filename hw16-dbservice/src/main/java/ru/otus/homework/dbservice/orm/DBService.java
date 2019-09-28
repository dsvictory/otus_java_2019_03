package ru.otus.homework.dbservice.orm;

import java.util.List;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Addressee;




public interface DBService extends Addressee {

    List<User> getUsers();
    
    long saveUser(User newUser);
    
    User getUser(long id);

}