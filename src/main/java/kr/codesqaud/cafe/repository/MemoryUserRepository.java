package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;


public class MemoryUserRepository implements UserRepository {

    private static Map<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        store.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public Optional<User> findByName(String name) {
        return store.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void updateUserName(String userId, String updateName) {
        User user = store.get(userId);
        user.setName(updateName);
        store.put(userId, user);
    }

    @Override
    public void updateUserEmail(String userId, String updateEmail) {
        User user = store.get(userId);
        user.setEmail(updateEmail);
        store.put(userId, user);
    }

    public void clearStore() {
        store.clear();
    }
}
