package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final Map<String, User> userRepository = new ConcurrentHashMap<>();
    private static Long sequence = 0L;
    @Override
    public User save(User user) {
        user.setSequence(++sequence);
        userRepository.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User findById(String id) {
        return userRepository.get(id);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

}
