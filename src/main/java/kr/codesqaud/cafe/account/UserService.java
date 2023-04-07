package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.ProfileSettingForm;
import kr.codesqaud.cafe.account.dto.UserForm;
import kr.codesqaud.cafe.account.exception.InvalidUserIdException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.codesqaud.cafe.exception.ErrorCode.INVALID_USER_ID_CODE;


@Service
public class UserService {

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

    public void update(ProfileSettingForm profileSettingForm, Long userId) {
        userRepository.update(profileSettingForm.setUser(findById(userId)));
    }

    public boolean isSamePassword(Long userId, String targetPassword) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> user.isSamePassword(targetPassword)).orElse(false);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean containsEmail(String email) {
        return userRepository.containsEmail(email);
    }

    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new InvalidUserIdException(INVALID_USER_ID_CODE);
        }
    }

    public boolean isDuplicateEmail(String targetEmail) {
        return userRepository.findByEmail(targetEmail).isEmpty();
    }
}
