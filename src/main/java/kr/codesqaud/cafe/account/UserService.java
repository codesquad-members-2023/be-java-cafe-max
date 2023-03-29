package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UsersRepository usersRepository;

	public UserService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public User createNewUser(UserForm userForm) {
		User user = new User(User.createNewId());
		user.setNickname(userForm.getNickname());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		usersRepository.add(user);
		return user;
	}

	public Optional<User> findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public List<UserForm> getAllMembers() {
		List<User> allMembers = usersRepository.getAllMembers();
		return allMembers.stream()
			.map(User::mappingUserForm)
			.collect(Collectors.toList());
	}

	public Optional<UserForm> findById(Long userId) {
		Optional<User> userOptional = usersRepository.findById(userId);
		return Optional.of(userOptional.get().mappingUserForm());
	}

	public void update(UserForm userForm, Long userId) {
		User user = usersRepository.findById(userId).get();
		user.setEmail(userForm.getEmail());
		user.setNickname(userForm.getNickname());
	}

	public boolean checkPassword(UserForm userForm, Long userId) {
		Optional<User> optionalUser = usersRepository.findById(userId);
		User user = optionalUser.get();
		return user.getPassword().equals(userForm.getPassword());
	}
}
