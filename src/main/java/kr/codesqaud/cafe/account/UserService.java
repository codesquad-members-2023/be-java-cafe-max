package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.LoginForm;
import kr.codesqaud.cafe.account.dto.ProfileEditForm;
import kr.codesqaud.cafe.account.dto.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int save(JoinForm joinForm) {
        User user = joinForm.toUser();
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm() {
        return userRepository.getAllUsers().stream()
                .map(UserForm::from)
                .collect(Collectors.toList());
    }

    public void update(ProfileEditForm profileEditForm, Long userId, BindingResult bindingResult) {
        User user = userRepository.findById(userId);
        if (!user.isSameEmail(profileEditForm.getEmail()) && isDuplicateEmail(profileEditForm.getEmail())) {
            bindingResult.rejectValue(EMAIL, "error.email.duplicate");
            loggingError(bindingResult);
            return;
        }
        if (!user.isSamePassword(profileEditForm.getPassword())) {
            bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
            loggingError(bindingResult);
            return;
        }
        userRepository.update(profileEditForm.setUser(findById(userId)));
    }

    public boolean isSamePassword(User user, String targetPassword) {
        return user.isSamePassword(targetPassword);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean containsEmail(String email) {
        return userRepository.containsEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId);
    }

    public boolean isDuplicateEmail(String targetEmail) {
        return userRepository.findByEmail(targetEmail).isPresent();
    }

    private static void loggingError(BindingResult bindingResult) {
        bindingResult.getAllErrors()
                .forEach(error -> logger.error("[ Name = {} ][ Message = {} ]", error.getObjectName(),
                        error.getDefaultMessage()));
    }

    public Long login(LoginForm loginForm, BindingResult bindingResult) {
        Optional<User> userOptional = findByEmail(loginForm.getEmail());
        if (userOptional.isEmpty()) {
            loggingError(bindingResult);
            bindingResult.rejectValue(EMAIL, "error.email.notExist");
            return Long.MIN_VALUE;
        }
        User user = userOptional.get();
        if (!isSamePassword(user, loginForm.getPassword())) {
            loggingError(bindingResult);
            bindingResult.rejectValue(PASSWORD, "error.password.notMatch");
            return Long.MIN_VALUE;
        }
        return user.getId();
    }
}
