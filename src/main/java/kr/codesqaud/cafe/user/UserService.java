package kr.codesqaud.cafe.user;

import kr.codesqaud.cafe.user.dto.JoinForm;
import kr.codesqaud.cafe.user.dto.LoginForm;
import kr.codesqaud.cafe.user.dto.ProfileEditForm;
import kr.codesqaud.cafe.user.dto.UserForm;
import kr.codesqaud.cafe.user.exception.IllegalEditEmailException;
import kr.codesqaud.cafe.user.exception.IllegalEditPasswordException;
import kr.codesqaud.cafe.user.exception.IllegalLoginPasswordException;
import kr.codesqaud.cafe.user.exception.NoSuchLoginEmailException;
import org.jasypt.encryption.StringEncryptor;
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
    private final StringEncryptor encryptor;
    private final UserRepository userRepository;

    public UserService(StringEncryptor encryptor, UserRepository userRepository) {
        this.encryptor = encryptor;
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(JoinForm joinForm) {
        User user = joinForm.toUser(encryptor);
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm(int page) {
        Pageable limit = PageRequest.of(page, 10);
        return userRepository.findAll(limit).stream()
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

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }


    public User checkLoginForm(LoginForm loginForm) {
        User user = findByEmail(loginForm.getEmail()).orElseThrow(NoSuchLoginEmailException::new);
        if (!isSamePassword(user, loginForm.getPassword())) {
            throw new IllegalLoginPasswordException();
        }
        return user;
    }

    public void checkEditInfo(User user, ProfileEditForm profileEditForm) {
        if (!user.isSameEmail(profileEditForm.getEmail()) && existsByEmail(profileEditForm.getEmail())) {
            throw new IllegalEditEmailException();
        }
        if (!isSamePassword(user, profileEditForm.getPassword())) {
            throw new IllegalEditPasswordException();
        }
    }
}
