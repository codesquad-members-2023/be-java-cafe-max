package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserJdbcRepository userJdbcRepository;

    public UserService(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    public String add(User user) {
        if (userJdbcRepository.containsUserId(user.getUserId())) {
            return null;
        }
        return userJdbcRepository.save(user);
    }

    public User getUser(String userId) {
        return userJdbcRepository.findByUserId(userId);
    }

    public List<User> getUserList() {
        return userJdbcRepository.findAll();
    }
}
