package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.account.form.JoinForm;
import kr.codesqaud.cafe.account.form.ProfileSettingForm;
import kr.codesqaud.cafe.account.form.UserForm;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createNewUser(JoinForm joinForm) {
		User user = joinForm.toUser();
		userRepository.save(user);
		return user;
	}

	public List<UserForm> getAllUsersForm() {
		List<User> allMembers = userRepository.getAllMembers();
		return allMembers.stream()
			.map(UserForm::from)
			.collect(Collectors.toList());
	}

	public void update(ProfileSettingForm profileSettingForm, Long userId) {
		userRepository.findById(userId).ifPresent(user -> user.setting(profileSettingForm));
	}

	public boolean checkPasswordByUserId(String password, Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		return optionalUser.map(user -> Objects.equals(user.getPassword(), password)).orElse(false);
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean containEmail(String email) {
		return userRepository.containEmail(email);
	}

	public Optional<User> findById(Long userId) {
		return userRepository.findById(userId);
	}
}
