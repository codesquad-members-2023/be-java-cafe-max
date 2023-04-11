package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.user.UserResponse;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    // 회원 가입
    public String join(User user){
        // 같은 이름, 같은 아이디가 있는 중복 회원X
        validateDuplicateUserName(user);
        validateDuplicateUserId(user);

        return userRepository.save(user).getUserId();
    }

    // 중복 이름 예외
    private void validateDuplicateUserName(User user) {
        // ifPresent = 값이 있으면. optional으로 감쌌기 때문에 가능하다.
        userRepository.findByName(user.getName())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 이름입니다.");
                });
    }

    // 중복 아이디 예외
    private void validateDuplicateUserId(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    // 전체 회원 조회
    public List<UserResponse> findUsers(){
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getUserId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public Optional<UserResponse> findByUserId(String userId) {
        User user = userRepository.findByUserId(userId).get();
        UserResponse userResponse = new UserResponse(user.getUserId(), user.getName(), user.getEmail());
        return Optional.ofNullable(userResponse);
    }

    public Optional<UserResponse> findByName(String name){
        User user = userRepository.findByName(name).get();
        UserResponse userResponse = new UserResponse(user.getUserId(), user.getName(), user.getEmail());
        return Optional.ofNullable(userResponse);
    }
}
