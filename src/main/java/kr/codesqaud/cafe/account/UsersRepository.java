package kr.codesqaud.cafe.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UsersRepository {

	private final List<User> usersRepository;

	public UsersRepository() {
		this.usersRepository = new ArrayList<>();
	}

	public boolean add(User user) {
		return usersRepository.add(user);
	}

	public boolean remove(User user) {
		return usersRepository.remove(user);
	}

	public List<User> getAllMembers() {
		return new ArrayList<>(usersRepository);
	}

	public Optional<User> findById(Long userId) {
		return usersRepository.stream()
			.filter(user -> Objects.equals(user.getId(), userId))
			.findAny();
	}
}
