package ru.otus.homework.frontend.socketSystem;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void addUser(User newUser);
}

