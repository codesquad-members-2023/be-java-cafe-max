package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemoryUserRepository implements UserRepository{
    private Map<String, User> repository = new HashMap<>();

    @Override
    public void save(User user) {
        repository.put(user.getUserId(), user);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public User getSpecificUser(String userId) {
        return repository.get(userId);
    }
}
