package kr.codesqaud.cafe.domain.user.repository;

import kr.codesqaud.cafe.domain.user.User;

import java.util.*;

public class UserMemoryRepository implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getIndex(), user);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<User>(users.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.values().stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElseGet(null));
    }
}
