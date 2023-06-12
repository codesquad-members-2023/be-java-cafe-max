package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id).get(); //
    }
    public User getUserByUserId(String userId) {
        return userRepository.getUserByUserId(userId).get();
    }
    public void update(User user){
        userRepository.update(user);
    }
}
