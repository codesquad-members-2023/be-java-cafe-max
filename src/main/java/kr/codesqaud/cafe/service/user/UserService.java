package kr.codesqaud.cafe.service.user;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.user.UserForm;
import kr.codesqaud.cafe.domain.dto.user.UserListForm;
import kr.codesqaud.cafe.domain.dto.user.UserProfileForm;
import kr.codesqaud.cafe.domain.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(UserForm form) {
        User user = new User(form.getUserId(), form.getPassword(), form.getName(), form.getEmail());
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new IllegalStateException("userId가 이미 존재");
                });
    }

    public List<UserListForm> findUsers() {
        // 이럴 때 필터링을 써주는구나
        // 다른 코드 참고하지 않고 직접 처음 생각해낸 코드 (패스워드를 뺀)
        return userRepository.findAll().stream()
                .map(UserListForm::from)
                .collect(Collectors.toList());
    }

    public UserProfileForm findProfile(String userId) {
        User user = findUserId(userId);
        return UserProfileForm.form(user);
    }

    public UserUpdateForm findUpdate(Long id) {
        User user = findUser(id);
        return UserUpdateForm.form(user);
    }

    private User findUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("이 유저 아이디를 찾을 수 없어: " + userId));
    }

    private User findUser(Long id) {
        // Optional을 스트림으로 처리
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("이 아이디를 찾을 수 없어: " + id));
    }

    public void updateUser(Long id, UserUpdateForm updateUser) {
        String password = updateUser.getPassword();
        if (!updateUser.isSamePassword(password)) {
            throw new IllegalStateException("비밀번호가 같지 않습니다.");
        }

        // updateUser의 정보들을 User에 덮어씌우기
        User originUser = findUser(id);
        originUser.setPassword(updateUser.getPassword());
        originUser.setName(updateUser.getName());
        originUser.setEmail(updateUser.getEmail());
        userRepository.update(id, originUser);
    }
}
