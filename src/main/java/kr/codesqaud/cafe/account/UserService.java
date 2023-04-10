package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import kr.codesqaud.cafe.account.dto.ProfileEditForm;
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

    public void update(User user, ProfileEditForm profileEditForm) {
        userRepository.update(profileEditForm.setUser(user));
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

}
