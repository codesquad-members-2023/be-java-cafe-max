package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.user.UserForm;
import kr.codesqaud.cafe.controller.dto.user.UserListForm;
import kr.codesqaud.cafe.controller.dto.user.UserProfileForm;
import kr.codesqaud.cafe.controller.dto.user.UserUpdateForm;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<User> users = userRepository.findAll();
        List<UserListForm> userListForms = new ArrayList<>();
        for (User user : users) {
            userListForms.add(UserListForm.from(user));
        }
        return userListForms;
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
