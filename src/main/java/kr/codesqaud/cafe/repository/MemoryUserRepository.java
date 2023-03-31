package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository {

    private final static Map<Long, User> userMap = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void join(User user) {
        user.setId(++sequence);
        userMap.put(user.getId(), user);
    }


    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }
}


