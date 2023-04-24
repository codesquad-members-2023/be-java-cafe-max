package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.login.AuthenticationDTO;
import kr.codesqaud.cafe.controller.dto.login.LoggedInDTO;
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

    public boolean checkLoginUser(AuthenticationDTO authenticationDTO) {
        Optional<User> loginUser = userRepository.findByUserId(authenticationDTO.getUserId());
        return loginUser.isPresent() && loginUser.get().matchPassword(authenticationDTO.getPassword());
    }

    public LoggedInDTO findByUserId(final String userId) {
        Optional<User> wantedId = userRepository.findByUserId(userId);
        return wantedId.map(LoggedInDTO::from).orElse(null);
    }
}
