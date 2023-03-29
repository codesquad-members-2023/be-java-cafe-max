package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.UserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user) {
        checkDuplicateId(user.getEmail());
        userRepository.save(user);
        return user.getEmail();
    }

    private void checkDuplicateId(String email) {
        userRepository.findByEmail(email)
                .ifPresent(m -> {
    throw new IllegalArgumentException("이미 존재하는 회원입니다.");});
    }
}
