package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public User save(User user) {
        users.put(sequence.incrementAndGet(), user.create(sequence.get()));
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
