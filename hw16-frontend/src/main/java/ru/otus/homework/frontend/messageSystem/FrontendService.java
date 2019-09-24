package ru.otus.homework.frontend.messageSystem;

import ru.otus.homework.frontend.domain.User;
import ru.otus.homework.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void addUser(User newUser);
}

