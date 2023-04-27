package kr.codesqaud.cafe.repository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.repository.impl.JDBCUserRepository;

@ExtendWith(MockitoExtension.class)
class JDBCUserRepositoryTest {

	@InjectMocks
	private JDBCUserRepository jDBCUserRepository;

	@Mock
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private User user;

	private static final String nickName = "nickName";

	private static final String email = "test@Email.com";

	private static final String password = "password";

	private static final String userId = "tester";

	@Captor
	private ArgumentCaptor<MapSqlParameterSource> paramCaptor;

	@BeforeEach
	void setUp() {
		user = new User(nickName, email, password, userId);
	}

	@Test
	void save() {
		//given
		MapSqlParameterSource expectedParams = new MapSqlParameterSource();
		expectedParams.addValue("nickName", user.getNickName());
		expectedParams.addValue("email", user.getEmail());
		expectedParams.addValue("password", user.getPassword());
		expectedParams.addValue("userId", user.getUserId());

		//when
		jDBCUserRepository.save(user);

		//then
		verify(namedParameterJdbcTemplate).update(
			eq("INSERT INTO `USER` (nickName, email, password, user_id) VALUES (:nickName, :email, :password, :userId)"),
			paramCaptor.capture()
		);

		assertThat(paramCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedParams);
	}
}