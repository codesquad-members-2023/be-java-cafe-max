package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

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
    public Optional<User> getSpecificUser(String userId) {
        return Optional.ofNullable(repository.get(userId));
    }
}
