package kr.codesqaud.cafe.repository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> storage = new HashMap<>();

    @Override
    public User save(User user) {
        storage.put(user.getUserId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<User> findByID(String userID) {
        return storage.values().stream()
                .filter(user -> user.getUserId().equals(userID))
                .findAny();
    }
}
