package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;

import java.util.*;

//@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> store = new HashMap<>();

    public void save(final User user) {
        store.put(user.getId(), user);
    }

    public Optional<User> findById(final String id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(final String email) {
        return store.values().stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    public List<User> findAllUser() {
        return new ArrayList<>(store.values());
    }



}
