package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<Long, User> userMap = new HashMap<>();

    @Override
    public void save(User user) {
        if (userMap.containsKey(user.getId())) {
            return;
        }

        userMap.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userMap.values().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public void update(Long id, User updateUser) {
        User findUser = findById(id).get();
        findUser.setName(updateUser.getName());
        findUser.setEmail(updateUser.getEmail());
        findUser.setPassword(updateUser.getPassword());
    }
}
