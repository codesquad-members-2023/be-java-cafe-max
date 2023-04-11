package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(LoginDTO loginDTO) {
        Optional<User> loginUser = userRepository.findByUserId(loginDTO.getUserId());
        return loginUser.isPresent() && loginUser.get().getPassword().equals(loginDTO.getPassword());
    }

    public void signIn(LoginDTO loginDTO) {
    }
}
