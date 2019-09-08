package ru.otus.homework.repostory;

import ru.otus.homework.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    long create(String name);
}
