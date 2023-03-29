package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserRequest;

import java.util.*;

public class MemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(UserRequest userRequest) {
        User user = userRequest.toEntity(++sequence);
        users.put(sequence, user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
