package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public User findOne(String userId) {
        return userRepository.findById(userId);
    }
}
