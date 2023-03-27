package codesquad.cafe.repository;

import codesquad.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static Map<Long, User> store = new HashMap<>();
    private static Long sequence = 0L;
    @Override
    public User save(User user) {
        store.put(++sequence, user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
