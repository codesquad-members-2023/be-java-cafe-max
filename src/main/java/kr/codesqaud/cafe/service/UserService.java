package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.RoginRequestDto;
import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.model.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignupRequestDto signupRequestDto) {
        userRepository.save(signupRequestDto);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId);
    }

    public String login(RoginRequestDto roginRequestDto) {
        User user = userRepository.findByEmail(roginRequestDto.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        if (!user.getPassword().equals(roginRequestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        return "토큰 DTO";
    }
}
