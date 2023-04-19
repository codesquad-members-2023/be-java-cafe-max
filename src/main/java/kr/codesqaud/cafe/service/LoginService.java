package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final JdbcUserRepository jdbcUserRepository;

    public LoginService(JdbcUserRepository jdbcUserRepository) {
        this.jdbcUserRepository = jdbcUserRepository;
    }

    public User login(String userId, String password) {
        return jdbcUserRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
