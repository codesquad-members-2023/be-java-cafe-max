package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static final Map<String, User> store = new ConcurrentHashMap<>(); // 동시성 문제

    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setId(++sequence);
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User findByUserId(String userId) {
        return store.get(userId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean isExists(String userId) {
        return store.containsKey(userId);
    }

    public void clearStore() {
        store.clear();
    }
}
