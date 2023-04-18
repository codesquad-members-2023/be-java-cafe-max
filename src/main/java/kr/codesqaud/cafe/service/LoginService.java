package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(JdbcUserRepository  jdbcUserRepository) {
        this.userRepository = jdbcUserRepository;
    }

    public User login(String userId, String password) {
        User user = userRepository.findByUserId(userId);

        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

}
