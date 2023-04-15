package kr.codesqaud.cafe.login;

import kr.codesqaud.cafe.user.User;
import kr.codesqaud.cafe.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return null: 로그인 실패
     */
    public User login(LoginRequestDto loginRequestDto) {
        return userRepository.findById(loginRequestDto.getUserId())
                .filter(u -> u.getPassword().equals(loginRequestDto.getPassword()))
                .orElse(null);
    }
}
