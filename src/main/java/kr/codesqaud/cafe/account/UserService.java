package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.LoginForm;
import kr.codesqaud.cafe.account.dto.ProfileEditForm;
import kr.codesqaud.cafe.account.dto.UserForm;
import kr.codesqaud.cafe.account.exception.IllegalEditEmailException;
import kr.codesqaud.cafe.account.exception.IllegalEditPasswordException;
import kr.codesqaud.cafe.account.exception.IllegalLoginPasswordException;
import kr.codesqaud.cafe.account.exception.NoSuchLoginEmailException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(JoinForm joinForm) {
        User user = joinForm.toUser();
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm() {
        return userRepository.findAll().stream()
                .map(UserForm::from)
                .collect(Collectors.toList());
    }

    public User update(User user, ProfileEditForm profileEditForm) {
        User entity = profileEditForm.setUser(user);
        return userRepository.save(entity);
    }

    public boolean isSamePassword(User user, String targetPassword) {
        return user.isSamePassword(targetPassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean containsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    public void checkId(User user, Long userId) {
        if (!user.isSameId(userId)) {
            throw new RuntimeException("접근 할 수 없습니다.");
        }
    }

    public void checkManager(User user) {
        if (!user.isManager()) {
            throw new RuntimeException("접근 할 수 없습니다.");
        }
    }

    public boolean isDuplicateEmail(User user, String email) {
        return !user.isSameEmail(email) && containsEmail(email);
    }

    public User checkLoginForm(LoginForm loginForm) {
        User user = findByEmail(loginForm.getEmail()).orElseThrow(NoSuchLoginEmailException::new);
        if (!isSamePassword(user, loginForm.getPassword())) {
            throw new IllegalLoginPasswordException();
        }
        return user;
    }

    public void checkEditInfo(User user, ProfileEditForm profileEditForm) {
        if (isDuplicateEmail(user, profileEditForm.getEmail())) {
            throw new IllegalEditEmailException();
        }
        if (!isSamePassword(user, profileEditForm.getPassword())) {
            throw new IllegalEditPasswordException();
        }
    }
}
