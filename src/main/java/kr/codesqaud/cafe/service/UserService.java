package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.user.UserForm;
import kr.codesqaud.cafe.controller.dto.user.UserProfileForm;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    // 서비스는 컨트롤러에서 DTO를 받아서 레포지토리에 domain으로 넘겨주는 역할
    // 위와 같이 이해했습니다.

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: 매개변수가 DTO를 의존하도록 변경
    public User join(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("userId가 이미 존재");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public UserProfileForm findProfile(Long id) {
        User user = findUser(id);
        return UserProfileForm.form(user);
    }

    public UserUpdateForm findUpdate(Long id) {
        User user = findUser(id);
        return UserUpdateForm.form(user);
    }

    private User findUser(Long id) {
        // Optional을 스트림으로 처리
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("이 아이디를 찾을 수 없어: " + id));
    }

    public void updateUser(Long id, UserUpdateForm updateUser) {
        // updateUser의 정보들을 User에 덮어씌우기
        User originUser = findUser(id);
        originUser.setPassword(updateUser.getPassword());
        originUser.setName(updateUser.getName());
        originUser.setEmail(updateUser.getEmail());
        userRepository.update(id, originUser);
    }
}
