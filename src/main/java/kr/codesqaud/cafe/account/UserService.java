package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.ProfileSettingForm;
import kr.codesqaud.cafe.account.dto.UserForm;
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

    public int save(JoinForm joinForm) {
        User user = joinForm.toUser();
        return userRepository.save(user);
    }

    public List<UserForm> getAllUsersForm() {
        return userRepository.getAllUsers().stream()
                .map(UserForm::from)
                .collect(Collectors.toList());
    }

    public void upsert(ProfileSettingForm profileSettingForm, Long userId) {
        userRepository.update(profileSettingForm.setUser(findById(userId)));
    }

    public boolean isSamePassword(Long userId, String targetPassword) {
        User user = userRepository.findById(userId);
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
}
