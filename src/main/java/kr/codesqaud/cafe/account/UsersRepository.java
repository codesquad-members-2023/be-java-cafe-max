package kr.codesqaud.cafe.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class UsersRepository {

	private final List<User> usersRepository;

	public static final AtomicLong atomicKey = new AtomicLong();

	public UsersRepository() {
		this.usersRepository = new ArrayList<>();
	}

	public boolean save(User user) {
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

	public Optional<User> findByEmail(String email) {
		return usersRepository.stream()
			.filter(user -> Objects.equals(user.getEmail(), email))
			.findAny();
	}

	public boolean containEmail(String email) {
		return usersRepository.stream()
			.anyMatch(user -> user.getEmail().equals(email));
	}

	public void clear() {
		usersRepository.clear();
	}
}
