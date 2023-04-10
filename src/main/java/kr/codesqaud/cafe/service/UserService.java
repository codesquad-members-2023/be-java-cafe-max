package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.UserForm;
import kr.codesqaud.cafe.repository.MemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private MemoryUserRepository memoryUserRepository;

    public UserService(MemoryUserRepository memoryUserRepository) {
        this.memoryUserRepository = memoryUserRepository;
    }

    public void saveUser(UserForm userForm) {
        User user = new User(userForm.getUserId(), userForm.getPassword(), userForm.getUserName(), userForm.getEmail());

        memoryUserRepository.save(user);
    }

    public List<User> getUserList() {
        return memoryUserRepository.getAllUsers();
    }

    public User findByUserId(String userId) {
        return memoryUserRepository.getSpecificUser(userId);
    }
}
