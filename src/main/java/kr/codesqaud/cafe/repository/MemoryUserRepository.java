package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public Optional<User> save(User user) {
        if (userMap.containsKey(user.getUserId())) {
            return Optional.empty();
        }

        userMap.put(user.getUserId(), user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
