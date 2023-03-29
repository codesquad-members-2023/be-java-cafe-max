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
    private final Map<String, User> userMap = new HashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public Optional<User> findByUserId(String userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    public Optional<User> findByEmail(String email) {
        return userMap.values().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }

    public User save(User user) {
        userMap.put(user.getUserId(), user);
        return user;
    }

    public int deleteAll() {
        int deleteUser = userMap.keySet().size();
        userMap.clear();
        return deleteUser;
    }
}
