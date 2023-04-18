package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.user.UserForm;
import kr.codesqaud.cafe.controller.user.UserDao;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    // 회원 가입

    public String join(UserForm form){
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());

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
    public List<UserDao> findUsers(){
        return userRepository.findAll().stream()
                .map(user -> new UserDao(user.getCustomerId(), user.getUserId(), user.getName(), user.getEmail()))
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDao findByUserId(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(()->new IllegalStateException("찾으시는 아이디는 없는 아이디 입니다."));
        return new UserDao(user.getCustomerId(), user.getUserId(), user.getName(), user.getEmail());
    }

    public UserDao findByName(String name){
        User user = userRepository.findByName(name).orElseThrow(()->new IllegalStateException("찾으시는 이름은 없는 이름 입니다."));
        return new UserDao(user.getCustomerId(), user.getUserId(), user.getName(), user.getEmail());
    }
}
