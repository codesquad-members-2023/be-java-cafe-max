package kr.codesqaud.cafe.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	public static final AtomicLong atomicKey = new AtomicLong();
	public static final String SAVE_USER_QUERY = "INSERT INTO USERS (NICKNAME, EMAIL, PASSWORD) values ( ?,?,? )";
	private final List<User> usersRepository;
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(List<User> usersRepository, DataSource dataSource) {
		this.usersRepository = usersRepository;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean save(User user) {
		int update = jdbcTemplate.update(
			SAVE_USER_QUERY, user.getNickname(), user.getEmail(), user.getPassword()
		);
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
