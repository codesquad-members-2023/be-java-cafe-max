package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.UserForm;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(MemoryUserRepository memoryUserRepository) {
        this.userRepository = memoryUserRepository;
    }

    public void saveUser(UserForm userForm) {
        User user = new User(userForm.getUserId(), userForm.getPassword(), userForm.getUserName(), userForm.getEmail());

        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.getAllUsers();
    }

    public User findByUserId(String userId) {
        return userRepository.getSpecificUser(userId);
    }
}
