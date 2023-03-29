package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.repository.MemoryUserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {


    private final MemoryUserRepository userRepository;

    @Autowired
    public UserService(MemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public String join(User user) {
        validateDuplicateMember(user); //중복 회원 검증
        userRepository.save(user);
        return user.getUserId();
    }
    private void validateDuplicateMember(User user) {
        userRepository.findByID(user.getUserId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    //회원목록 조회
    public List<User> checkUsers() {
        return userRepository.findAll();
    }


    public User getUserProfile(String userId) {
        return userRepository.findByID(userId).get();
    }
}
