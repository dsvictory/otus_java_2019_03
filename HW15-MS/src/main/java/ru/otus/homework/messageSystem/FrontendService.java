package ru.otus.homework.messageSystem;

import ru.otus.homework.domain.User;
import ru.otus.homework.messageSystem.Addressee;
import java.util.*;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {
    void init();

    void setUsers(List<User> users);

}

