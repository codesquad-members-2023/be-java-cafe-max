package kr.codesqaud.cafe.domain.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    // key: 사용자 아이디(userId), value: User 객체
    private final Map<String, User> store = new HashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }

    public User save(User user) {
        store.put(user.getUserId(), user);
        return user;
    }

    public int deleteAll() {
        int deleteUserCount = store.keySet().size();
        store.clear();
        return deleteUserCount;
    }
}
