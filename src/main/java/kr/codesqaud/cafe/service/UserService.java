package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.user.UserProfileForm;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public UserProfileForm findProfile(Long id) {
        User user = findUser(id);
        return new UserProfileForm(user);
    }

    public UserUpdateForm findUpdate(Long id) {
        User user = findUser(id);
        return new UserUpdateForm(user);
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
