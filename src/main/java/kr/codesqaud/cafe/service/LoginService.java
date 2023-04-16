package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User login(String userId, String password){
        return userRepository.findByUserId(userId)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
