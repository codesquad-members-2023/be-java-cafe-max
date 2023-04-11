package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        users.put(++sequence, user.create(sequence));
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.values().stream()
                .filter(user -> userId.equals(user.getUserId()))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
