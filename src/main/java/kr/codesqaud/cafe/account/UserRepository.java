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
	public static final String COLUMN_USER_ID = "user_id";
	public static final String COLUMN_PASSWORD = "password";
	public static final String COLUMN_NICKNAME = "nickname";
	public static final String COLUMN_EMAIL = "email";
	private final JdbcTemplate jdbcTemplate;

	public UserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void save(User user) {
		jdbcTemplate.update(
			QUERY_SAVE, user.getNickname(), user.getEmail(), user.getPassword()
		);
	}

	public List<User> getAllMembers() {
		return jdbcTemplate.query(QUERY_FIND_ALL_USERS, (resultSet, rowNum)
			-> new User.Builder(resultSet.getLong(COLUMN_USER_ID))
			.password(resultSet.getString(COLUMN_PASSWORD).trim())
			.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
			.email(resultSet.getString(COLUMN_EMAIL).trim())
			.build()
		);
	}

	public Optional<User> findById(Long userId) {
		RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder(userId)
			.email(resultSet.getString(COLUMN_EMAIL))
			.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
			.password(resultSet.getString(COLUMN_PASSWORD).trim())
			.build();
		return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, userRowMapper, userId));
	}

	public Optional<User> findByEmail(String email) {
		RowMapper<User> userRowMapper = (resultSet, rowNum) -> new User.Builder(resultSet.getLong(COLUMN_USER_ID))
			.nickname(resultSet.getString(COLUMN_NICKNAME).trim())
			.password(resultSet.getString(COLUMN_PASSWORD).trim())
			.build();

		return Optional.ofNullable(this.jdbcTemplate.queryForObject(QUERY_FIND_BY_EMAIL, userRowMapper, email));
	}

	public boolean containEmail(String email) {
		return jdbcTemplate.queryForObject(QUERY_CONTAINS_EMAIL, Integer.class, email) != 0;
	}

	public void clear() {
		jdbcTemplate.execute("DELETE FROM USERS");
	}

	public void update(ProfileSettingForm profileSettingForm, User user) {
		jdbcTemplate.update(QUERY_UPDATE, profileSettingForm.getNickname(), profileSettingForm.getEmail(),
			user.getId());
	}
}
