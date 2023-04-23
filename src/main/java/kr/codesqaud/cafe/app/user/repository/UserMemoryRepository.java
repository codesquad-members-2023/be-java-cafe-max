package kr.codesqaud.cafe.app.user.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserMemoryRepository implements UserRepository {

    private final List<User> store = new ArrayList<>();
    private static long sequence = 0;

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public Optional<User> findById(Long id) {
        return store.stream().filter(user -> user.getId().equals(id)).findAny();
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return store.stream().filter(user -> user.getUserId().equals(userId)).findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    @Override
    public User save(User user) {
        User newUser = new User(nextId(), user.getUserId(), user.getPassword(), user.getName(),
            user.getEmail());
        store.add(newUser);
        return newUser;
    }

    @Override
    public User modify(User user) {
        store.remove(user);
        store.add(user);
        return user;
    }

    @Override
    public synchronized int deleteAll() {
        int deleteUserCount = store.size();
        store.clear();
        sequence = 0;
        return deleteUserCount;
    }

    private synchronized Long nextId() {
        return ++sequence;
    }
}
