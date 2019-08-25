package ru.otus.homework.repostory;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.User;
import ru.otus.homework.orm.ORMTemplate;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ORMTemplate<User> orm;

    public UserRepositoryImpl(ORMTemplate<User> ormTemplate) {
        this.orm = ormTemplate;
    }

    @Override
    public List<User> findAll() {
        return orm.getAll(User.class);
    }

    @Override
    public long create(String name) {
        return orm.saveEntity(new User(name));
    }
}
