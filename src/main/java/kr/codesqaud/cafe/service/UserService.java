package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.dto.SignupRequestDto;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signup(SignupRequestDto signupRequestDto) {


        return "회원가입 성공 DTO";
    }
}
