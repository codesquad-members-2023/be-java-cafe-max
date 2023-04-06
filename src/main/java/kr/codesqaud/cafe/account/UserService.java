package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.exception.InvalidUserIdException;
import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.ProfileSettingForm;
import kr.codesqaud.cafe.account.dto.UserForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static kr.codesqaud.cafe.exception.ErrorCode.INVALID_USER_ID_CODE;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int saveNewUser(JoinForm joinForm) {
        User user = joinForm.toUser();
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm() {
        List<User> allMembers = userRepository.getAllMembers();
        return allMembers.stream()
                .map(UserForm::from)
                .collect(Collectors.toList());
    }

    public void update(ProfileSettingForm profileSettingForm, Long userId) {
        userRepository.update(profileSettingForm.setUser(findById(userId)));
    }

    public boolean checkPassword(String password, String targetPassword) {
        return Objects.equals(targetPassword, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean containEmail(String email) {
        return userRepository.containEmail(email);
    }

    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new InvalidUserIdException(INVALID_USER_ID_CODE);
        }
    }

    public boolean isDuplicateEmail(String defaultEmail, String targetEmail) {
        return Objects.equals(defaultEmail, targetEmail)
                && containEmail(targetEmail);
    }
}
