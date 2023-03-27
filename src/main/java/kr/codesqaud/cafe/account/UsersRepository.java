package kr.codesqaud.cafe.account;

import java.util.ArrayList;
import java.util.List;

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
}
