package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.dto.LoginForm;
import kr.codesquad.cafe.user.dto.ProfileEditForm;
import kr.codesquad.cafe.user.dto.UserForm;
import kr.codesquad.cafe.user.exception.*;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class UserService {
    private static final int POST_DEFAULT_PAGE_SIZE = 10;
    private final StringEncryptor encryptor;
    private final UserRepository userRepository;

    public UserService(StringEncryptor encryptor, UserRepository userRepository) {
        this.encryptor = encryptor;
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(JoinForm joinForm) {
        if (existsByEmail(joinForm.getEmail())) {
            throw new ExistsEmailException();
        }
        User user = joinForm.toUser(encryptor);
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm(int page) {
        Pageable pageable = PageRequest.of(page, POST_DEFAULT_PAGE_SIZE);
        Page<User> users = userRepository.findAll(pageable);
        return toUserForm(users);
    }

    private static List<UserForm> toUserForm(Page<User> userPage) {
        return userPage.stream()
                .map(UserForm::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public User update(User user, ProfileEditForm profileEditForm) {
        User changedUser = profileEditForm.setUser(user);
        return userRepository.save(changedUser);
    }

    public boolean isSamePassword(User user, String targetPassword) {
        return user.isSamePassword(encryptor, targetPassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User checkLoginForm(LoginForm loginForm) {
        User user = findByEmail(loginForm.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!isSamePassword(user, loginForm.getPassword())) {
            throw new IncorrectPasswordException();
        }
        return user;
    }

    /**
     * 프로필 수정과 로그인에서 다른 페이지를 반환하여야 2개의 PasswordException 구현
     */
    public void checkEditInfo(User user, ProfileEditForm profileEditForm) {
        if (!user.isSameEmail(profileEditForm.getEmail()) && existsByEmail(profileEditForm.getEmail())) {
            throw new DuplicateEmailException();
        }
        if (!isSamePassword(user, profileEditForm.getPassword())) {
            throw new InvalidPasswordException();
        }
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

}
