package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;
import kr.codesqaud.cafe.user.controller.request.UserRegisterRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public User save(UserRegisterRequest userRegisterRequest) {
        User user = userRegisterRequest.toEntity();
        users.put(userRegisterRequest.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
