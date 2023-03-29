package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private static final Map<String, User> store = new ConcurrentHashMap<>(); // 동시성 문제

    public User save(User user) {
        store.put(user.getUserId(), user);
        return user;
    }

    public User findByUserId(String userId) { return store.get(userId); }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
