package codesquad.cafe.user.repository;

import codesquad.cafe.user.domain.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryUserRepository implements UserRepository {
    private static Map<String, User> store = new ConcurrentHashMap<>();
    @Override
    public User save(final User user) {
        store.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(final String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    @Override
    public void update(final User user) {
        save(user);
    }

    @Override
    public String findNameById(final String id) {
        return null;
    }
}
