package kr.codesqaud.cafe.repository.user;

import static kr.codesqaud.cafe.repository.user.UserSql.*;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;

@Repository
public class JdbcUserRepository implements UserRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public User create(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		namedParameterJdbcTemplate.update(CREATE, params);
		return user;
	}

	@Override
	public boolean update(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		namedParameterJdbcTemplate.update(UPDATE, params);
		return true;
	}

	@Override
	public Optional<User> findByUserID(String userID) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userID", userID);
		List<User> users = namedParameterJdbcTemplate.query(FIND_BY_USERID, param, userRowMapper());
		return optionalTo(users);
	}

	private Optional<User> optionalTo(List<User> users) {
		return users.stream().findAny();
	}

	@Override
	public List<User> findAll() {
		return namedParameterJdbcTemplate.query(SELECT_ALL_FOR_USER_LIST, userRowMapper());
	}

	@Override
	public boolean existUserID(String userID) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userID", userID);
		return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(EXISTS_USERID, param, Boolean.class));
	}

	@Override
	public boolean existUpdateEmail(String userID, String email) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userID", userID)
			.addValue("email", email);
		return Boolean.TRUE.equals(
			namedParameterJdbcTemplate.queryForObject(EXISTS_UPDATE_EMAIL, param, Boolean.class));
	}

	@Override
	public boolean existUpdateNickname(String userID, String nickname) {
		SqlParameterSource param = new MapSqlParameterSource()
			.addValue("userID", userID)
			.addValue("nickname", nickname);
		return Boolean.TRUE.equals(
			namedParameterJdbcTemplate.queryForObject(EXISTS_UPDATE_NICKNAME, param, Boolean.class));
	}

	private RowMapper<User> userRowMapper() {
		return (rs, rowNum) -> new User(rs.getLong("userIndex"), rs.getString("userID"), rs.getString("email"),
			rs.getString("nickname"), rs.getString("password"), rs.getDate("signUpDate").toLocalDate());
	}
}
