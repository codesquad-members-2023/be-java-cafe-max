package codesquad.cafe.domain.user.repository;

import codesquad.cafe.domain.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
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
        return new ArrayList<>(store.values());
    }
}
