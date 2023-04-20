package kr.codesqaud.cafe.account.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.repository.UserRepository;

@Repository
@Qualifier("jdbcRepository")
public class JDBCUserRepository implements UserRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JDBCUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void save(User user) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO `USER` (nickName, email,password,user_id) VALUES (:nickName,:email, :password, :userId)",
			new MapSqlParameterSource()
				.addValue("nickName", user.getNickName())
				.addValue("email", user.getEmail())
				.addValue("password", user.getPassword())
				.addValue("userId", user.getUserId()));
	}

	/**
	 * db에 같은 id가 존재한다면 return true;
	 * @param userId
	 * @return
	 */
	@Override
	public boolean exist(String userId) {
		return namedParameterJdbcTemplate.query("SELECT user_id FROM `USER` WHERE user_id = :userId LIMIT 1 ",
				new MapSqlParameterSource("userId", userId), (rs, rn) -> rs.getString("userId"))
			.stream()
			.findFirst()
			.isPresent();
	}

	@Override
	public List<User> findAll() {
		return namedParameterJdbcTemplate.query("SELECT nickName,email,password,user_id,date FROM `USER`",
			(rs, rn) -> new User(rs));
	}

	@Override
	public Optional<User> findUserById(String userId) {
		List<User> users = namedParameterJdbcTemplate.query("SELECT * FROM `USER` WHERE user_id = :userId",
			new MapSqlParameterSource("userId", userId), (rs, rn) -> new User(rs));
		return users.stream().findFirst();
	}

	@Override
	public void updateUser(User user) {
		namedParameterJdbcTemplate.update(
			"UPDATE `USER` SET nickName = :nickName, email = :email, password = :password WHERE user_id = :userId",
			new MapSqlParameterSource()
				.addValue("nickName", user.getNickName())
				.addValue("email", user.getEmail())
				.addValue("password", user.getPassword())
				.addValue("userId", user.getUserId())
		);
	}
}
