package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserForm;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(JdbcUserRepository jdbcUserRepository) {
        this.userRepository = jdbcUserRepository;
    }

    public void saveUser(UserForm userForm) {
        User user = User.builder()
                .userId(userForm.getUserId())
                .password(userForm.getPassword())
                .userName(userForm.getUserName())
                .email(userForm.getEmail())
                .build();

        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
