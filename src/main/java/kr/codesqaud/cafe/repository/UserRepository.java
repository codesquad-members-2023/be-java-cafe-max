package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private Map<String, User> repository = new HashMap<>();

    public void save(User user) {
        repository.put(user.getUserId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(repository.values());
    }

    public User getSpecificUser(String userId) {
        return repository.get(userId);
    }
}
