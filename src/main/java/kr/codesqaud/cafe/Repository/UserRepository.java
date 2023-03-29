package kr.codesqaud.cafe.Repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    private final Map<String, User> store = new HashMap<>();

    public User save(User user) {
        store.put(user.getId(), user);
        return user;
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(store.get(email));
    }

    public List<User> findAllUser() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
