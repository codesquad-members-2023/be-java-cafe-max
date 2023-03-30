package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public void save(User user) {
        if (userMap.containsKey(user.getUserId())) {
            return;
        }

        userMap.put(user.getUserId(), user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }
}
