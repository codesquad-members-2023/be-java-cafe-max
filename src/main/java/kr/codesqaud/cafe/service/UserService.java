package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.ArticleRepository;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    public UserService(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public boolean join(User user) {
       if (userRepository.findById(user.getUserId()).isPresent()) {
           return false;
       }
       userRepository.save(user);
       return true;
    }

    public boolean login(String userId, String password) {

        if (!userRepository.findById(userId).isPresent()) {
            return false;
        }
        User user = findById(userId);
        return user.checkPassword(password);
    }

    public void update(User user, String updateName, String updateEmail) {
        articleRepository.updateWriter(user.getName(), updateName);
        User updatedUser = user.updateNameAndEmail(updateName, updateEmail);
        userRepository.updateUser(updatedUser);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException :: new);
    }

    public User findByName(String name) {
        return userRepository.findByName(name).orElseThrow();
    }
}
