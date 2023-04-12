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
       if (findById(user.getUserId()).isPresent()) {
           return false;
       }
       userRepository.save(user);
       return true;
    }

    public boolean login(String userId, String password) {
        Optional<User> user = findById(userId);

        if (!user.isPresent()) {
            return false;
        }
        if (user.get().getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void update(User user, String updateName, String updateEmail) {
        userRepository.updateUserName(user.getUserId(), updateName);
        userRepository.updateUserEmail(user.getUserId(), updateEmail);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
