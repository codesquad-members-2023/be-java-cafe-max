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

	private final UsersRepository usersRepository;

	public UserService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public User createNewUser(JoinForm joinForm) {
		User user = new User.Builder(UsersRepository.atomicKey.incrementAndGet())
			.nickname(joinForm.getNickname())
			.email(joinForm.getEmail())
			.password(joinForm.getPassword())
			.build();
		usersRepository.save(user);
		return user;
	}

	public List<UserForm> getAllUsersForm() {
		List<User> allMembers = usersRepository.getAllMembers();
		return allMembers.stream()
			.map(User::mappingUsersForm)
			.collect(Collectors.toList());
	}

	public void update(ProfileSettingForm profileSettingForm, Long userId) {
		usersRepository.findById(userId).ifPresent(user -> user.setting(profileSettingForm));
	}

	public boolean checkPasswordByUserId(String password, Long userId) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		return optionalUser.map(user -> Objects.equals(user.getPassword(), password)).orElse(false);
	}

}
