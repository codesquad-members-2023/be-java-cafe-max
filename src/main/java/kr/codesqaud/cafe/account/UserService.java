package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.account.exception.AccountException;
import kr.codesqaud.cafe.account.exception.ErrorCode;
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
		return userRepository.findByEmail(joinForm.getEmail()).get();
	}

	public List<UserForm> getAllUsersForm() {
		List<User> allMembers = userRepository.getAllMembers();
		return allMembers.stream()
			.map(UserForm::from)
			.collect(Collectors.toList());
	}

	public void update(ProfileSettingForm profileSettingForm, Long userId) {
		User user = userRepository.findById(userId);
		userRepository.update(profileSettingForm.setUser(user));
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
		try {
			return userRepository.findById(userId);
		} catch (AccountException e) {
			throw new AccountException(e, ErrorCode.INVALID_ID_CODE);
		}
	}

	public boolean isDuplicateEmail(String defaultEmail, String targetEmail) {
		return Objects.equals(defaultEmail, targetEmail)
			&& containEmail(targetEmail);
	}
}
