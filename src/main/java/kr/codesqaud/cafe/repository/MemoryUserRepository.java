package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryUserRepository implements UserRepository {
    private static final Map<String, User> userRepository = new ConcurrentHashMap<>();
    private static final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public User save(User user) {
        long newId = sequence.incrementAndGet();
        user.setId(newId);
        userRepository.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(userRepository.get(userId));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.values());
    }

    @Override
    public void update(User user) {
        userRepository.put(user.getUserId(), user);
    }
}
