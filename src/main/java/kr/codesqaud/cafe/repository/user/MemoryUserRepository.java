package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> STORE = new ConcurrentHashMap<>();

    @Override
    public String save(final User user) {
        STORE.put(user.getUserId(), user);
        return user.getUserId();
    }

    @Override
    public User findByUserId(String userId) {
        return STORE.get(userId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(STORE.values());
    }

    @Override
    public boolean exist(String userId) {
        return STORE.containsKey(userId);
    }

    @Override
    public int update(final User user) {
        STORE.put(user.getUserId(), user);
        return 1;
    }
}
