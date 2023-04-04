package kr.codesqaud.cafe.domain.member.repository;

import kr.codesqaud.cafe.domain.member.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UserMemoryImpl implements UserRepository {

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
    public User findById(String id) {
        return users.values().stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElseGet(null);
    }
}
