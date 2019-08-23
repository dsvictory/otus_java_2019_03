package ru.otus.homework.repostory;

import org.springframework.stereotype.Repository;
import ru.otus.homework.domain.User;
import ru.otus.homework.services.ORMManager;
import ru.otus.homework.services.ORMTemplate;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final ORMTemplate<User> orm;

    public UserRepositoryImpl(ORMManager<User> ormManager) {
        this.orm = ormManager.getORM();
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
