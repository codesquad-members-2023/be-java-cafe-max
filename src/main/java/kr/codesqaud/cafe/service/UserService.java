package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean join(User user) {
       if (findOne(user.getUserId()).isPresent()) {
           return false;
       }
       userRepository.save(user);
       return true;
    }

    public boolean login(String userId, String password) {
        if (findOne(userId).get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }
}
