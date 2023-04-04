package kr.codesqaud.cafe.repository.impl;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryUserRepository implements UserRepository {

    private final Map<String, User> userRepository;

    public MemoryUserRepository() {
        this.userRepository = new LinkedHashMap<>();
    }

    @Override
    public void save(User user) {
        userRepository.put(user.getId(), user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<User> findUserById(String userId) {
        return userRepository.values().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();
    }

    @Override
    public void updateUser(User user) {
        save(user);
    }
}
