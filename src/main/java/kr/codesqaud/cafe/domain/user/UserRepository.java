package kr.codesqaud.cafe.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    // key: 사용자 등록번호(Long), value: User 객체
    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0;

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByUserId(String userId) {
        return store.values().stream()
            .filter(user -> user.getUserId().equals(userId))
            .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public synchronized int deleteAll() {
        int deleteUserCount = store.keySet().size();
        store.clear();
        sequence = 0;
        return deleteUserCount;
    }

    public synchronized Long nextId() {
        return ++sequence;
    }
}
