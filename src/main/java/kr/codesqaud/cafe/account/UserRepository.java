package kr.codesqaud.cafe.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.account.form.ProfileSettingForm;

@Repository
public class UserRepository {

	public static final AtomicLong atomicKey = new AtomicLong();
	//language=H2
	private static final String QUERY_SAVE = "INSERT INTO USERS (NICKNAME, EMAIL, PASSWORD) values ( ?,?,? )";
	//language=H2
	private static final String QUERY_UPDATE = "UPDATE USERS SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";
	private final List<User> usersRepository;
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(List<User> usersRepository, DataSource dataSource) {
		this.usersRepository = usersRepository;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public boolean save(User user) {
		int update = jdbcTemplate.update(
			QUERY_SAVE, user.getNickname(), user.getEmail(), user.getPassword()
		);
		return usersRepository.add(user);
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

	public void update(ProfileSettingForm profileSettingForm, User user) {
		int update = jdbcTemplate.update(QUERY_UPDATE
			, user.getNickname(), user.getEmail(), user.getId());
		System.out.println(update);
		usersRepository.remove(user);
		save(profileSettingForm.toUser(user));
	}
}
