package kr.codesqaud.cafe.account;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.account.form.ProfileSettingForm;

@Repository
public class UserRepository {

	public static final AtomicLong atomicKey = new AtomicLong();
	private static final String QUERY_SAVE = "INSERT INTO USERS (NICKNAME, EMAIL, PASSWORD) values ( ?,?,? )";
	private static final String QUERY_UPDATE = "UPDATE USERS SET NICKNAME = ?, EMAIL = ? WHERE USER_ID = ?";
	private static final String QUERY_FIND_BY_ID = "SELECT EMAIL,NICKNAME,PASSWORD FROM USERS WHERE USER_ID = ?";
	private static final String QUERY_FIND_BY_EMAIL = "SELECT USER_ID,NICKNAME,PASSWORD FROM USERS WHERE EMAIL = ?";
	private static final String QUERY_CONTAINS_EMAIL = "SELECT COUNT(*) FROM USERS WHERE EMAIL = ?";
	private static final String QUERY_FIND_ALL_USERS = "SELECT * FROM USERS";
	private final List<User> usersRepository;
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(List<User> usersRepository, DataSource dataSource) {
		this.usersRepository = usersRepository;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void save(User user) {
		jdbcTemplate.update(
			QUERY_SAVE, user.getNickname(), user.getEmail(), user.getPassword()
		);
	}

	public List<User> getAllMembers() {
		return jdbcTemplate.query(QUERY_FIND_ALL_USERS, (resultSet, rowNum)
			-> new User.Builder(resultSet.getLong("user_id"))
			.password("password")
			.nickname("nickname")
			.email("email")
			.build()
		);
	}

	public Optional<User> findById(Long userId) {
		RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder(userId)
			.email(resultSet.getString("email"))
			.nickname(resultSet.getString("nickname"))
			.password(resultSet.getString("password"))
			.build();
		return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, userRowMapper, userId));
	}

	public Optional<User> findByEmail(String email) {
		RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder(resultSet.getLong("user_id"))
			.nickname(resultSet.getString("nickname"))
			.password(resultSet.getString("password"))
			.build();

		return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL, userRowMapper, email));
	}

	public boolean containEmail(String email) {
		return jdbcTemplate.queryForObject(QUERY_CONTAINS_EMAIL, Integer.class, email) != 0;
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
