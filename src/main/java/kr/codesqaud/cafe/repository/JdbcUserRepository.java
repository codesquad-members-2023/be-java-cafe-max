package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

public class JdbcUserRepository implements UserRepository {
	private final JdbcTemplate jdbcTemplate;

	public JdbcUserRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public User save(User user) {
		String sql = "INSERT INTO USER_INFO(userID, email, nickname, password, signUpDate) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getUserID(), user.getEmail(), user.getNickname(),
			user.getPassword(), user.getSignUpDate());
		return user;
	}

	@Override
	public boolean update(UserDto userDto) {
		String sql = "UPDATE USER_INFO SET email = ?, nickname = ?, password = ? WHERE userID = ?";
		jdbcTemplate.update(sql, userDto.getEmail(), userDto.getNickname(), userDto.getPassword(), userDto.getUserID());
		return true;
	}

	@Override
	public Optional<User> findByUserID(String userID) {
		String sql = "SELECT * FROM USER_INFO WHERE userID = ?";
		return jdbcTemplate.query(sql, userRowMapper(), userID).stream().findAny();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		String sql = "SELECT * FROM USER_INFO WHERE email = ?";
		return jdbcTemplate.query(sql, userRowMapper(), email).stream().findAny();
	}

	@Override
	public Optional<User> findByNickname(String nickname) {
		String sql = "SELECT * FROM USER_INFO WHERE nickname = ?";
		return jdbcTemplate.query(sql, userRowMapper(), nickname).stream().findAny();
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM USER_INFO";
		return jdbcTemplate.query(sql, userRowMapper());
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> new User(rs.getLong("index"), rs.getString("userID"), rs.getString("email"),
			rs.getString("nickname"), rs.getString("password"), rs.getDate("signUpDate").toLocalDate());
	}
}
