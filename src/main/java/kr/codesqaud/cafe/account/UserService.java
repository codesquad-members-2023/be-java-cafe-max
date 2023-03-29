package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.account.form.JoinForm;

@Service
public class UserService {

	private final UsersRepository usersRepository;

	public UserService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public User createNewUser(JoinForm joinForm) {
		User user = new User(User.createNewId());
		user.setNickname(joinForm.getNickname());
		user.setEmail(joinForm.getEmail());
		user.setPassword(joinForm.getPassword());
		usersRepository.save(user);
		return user;
	}

	public List<UserForm> getAllUsersForm() {
		List<User> allMembers = usersRepository.getAllMembers();
		return allMembers.stream()
			.map(User::mappingUserForm)
			.collect(Collectors.toList());
	}

	public void update(UserForm userForm, Long userId) {
		User user = usersRepository.findById(userId).get();
		user.setEmail(userForm.getEmail());
		user.setNickname(userForm.getNickname());
	}

	public boolean checkPasswordByUserId(String password, Long userId) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		return optionalUser.map(user -> Objects.equals(user.getPassword(), password)).orElse(false);
	}

}
