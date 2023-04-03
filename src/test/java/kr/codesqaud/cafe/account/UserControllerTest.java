package kr.codesqaud.cafe.account;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
	public static final String JACK_EMAIL = "jack@email.com";
	public static final String JACK_PASSWORD = "123456789a";
	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserRepository userRepository;

	@DisplayName("로그인 페이지 열람")
	@Test
	void showLoginPage() throws Exception {
		mockMvc.perform(get("/users/login"))
			.andExpect(status().isOk());
	}

	@DisplayName("로그인 - 성공")
	@Test
	void loginSuccess() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param("email", JACK_EMAIL)
				.param("password", JACK_PASSWORD))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("/users/*"));
	}

	@DisplayName("로그인 - 비밀번호 실패")
	@Test
	void loginFailedByPassword() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param("email", JACK_EMAIL)
				.param("password", "12345678"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/login"));
	}

	@DisplayName("로그인 - 이메일 주소 실패")
	@Test
	void loginFailedByEmail() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param("email", "jack1@email.com")
				.param("password", "12345ddd"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/login"));
	}

	@DisplayName("가입 페이지 열람")
	@Test
	void showJoinPage() throws Exception {
		mockMvc.perform(get("/users/join"))
			.andExpect(model().attributeExists("joinForm"))
			.andExpect(status().isOk());
	}

	@DisplayName("유저 추가 - 성공")
	@Test
	void addUserSuccess() throws Exception {
		mockMvc.perform(post("/users/join")
				.param("email", JACK_EMAIL)
				.param("nickname", "jack")
				.param("password", JACK_PASSWORD))
			.andExpect(status().is3xxRedirection());

		assertThat(userRepository.findByEmail(JACK_EMAIL)).isPresent();
	}

	@DisplayName("유저 추가 - 실패")
	@ParameterizedTest
	@CsvSource({"sss,jack,a123456789", "jack@email.com,j,a1223456789", "jack@email.com,jack,123456789"})
	void addUserFailed(String email, String nickname, String password) throws Exception {
		mockMvc.perform(post("/users/join")
				.param("email", email)
				.param("nickname", nickname)
				.param("password", password))
			.andExpect(status().isOk())
			.andExpect(view().name("account/join"));
	}

	@DisplayName("맴버 리스트 페이지 열람")
	@Test
	void showUsers() throws Exception {
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("users"))
			.andExpect(view().name("account/members"));
	}

	@DisplayName("유저 프로필 페이지 열람 - 성공")
	@Test
	void showUserSuccess() throws Exception {
		User user = saveAndGetUserJack();
		mockMvc.perform(get("/users/" + user.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userId"))
			.andExpect(model().attributeExists("profileForm"))
			.andExpect(view().name("account/profile"));
	}

	@DisplayName("유저 프로필 페이지 열람 - 실패")
	@Test
	void showUserFailed() throws Exception {
		mockMvc.perform(get("/users/20"))
			.andExpect(status().is3xxRedirection());
	}

	@DisplayName("유저 프로필 수정 페이지 열람  - 성공")
	@Test
	void showUserProfileSuccess() throws Exception {
		User user = saveAndGetUserJack();
		mockMvc.perform(get("/users/" + user.getId() + "/update"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("userId"))
			.andExpect(model().attributeExists("profileSettingForm"))
			.andExpect(view().name("account/profileUpdate"));
	}

	@DisplayName("유저 프로필 수정 페이지 열람  - 실패")
	@Test
	void showUserProfileFailed() throws Exception {
		mockMvc.perform(get("/users/20/update"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@DisplayName("유저 프로필 세팅 - 성공")
	@Test
	void setUserProfileSuccess() throws Exception {
		User user = saveAndGetUserJack();
		String mail = "jerry@email.com";
		String jerry = "jerry";
		mockMvc.perform(put("/users/" + user.getId() + "/update")
				.param("password", user.getPassword())
				.param("email", mail)
				.param("nickname", jerry))
			.andExpect(status().is3xxRedirection());

		Optional<User> userOptional = userRepository.findById(user.getId());
		assertThat(userOptional).isPresent();
		User changedUser = userOptional.get();
		assertThat(changedUser.getEmail()).isEqualTo(mail);
		assertThat(changedUser.getNickname()).isEqualTo(jerry);
	}

	@DisplayName("유저 프로필 세팅 - 실패(비밀번호 불 일치)")
	@Test
	void setUserProfileFailedByPassword() throws Exception {
		User user = saveAndGetUserJack();
		String mail = "jerry@email.com";
		String jerry = "jerry";
		mockMvc.perform(put("/users/" + user.getId() + "/update")
				.param("password", "987654123a")
				.param("email", mail)
				.param("nickname", jerry))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/profileUpdate"));
	}

	@DisplayName("유저 프로필 세팅 - 실패(유저 아이디)")
	@Test
	void setUserProfileFailedByUserId() throws Exception {
		User user = saveAndGetUserJack();
		String mail = "jerry@email.com";
		String jerry = "jerry";
		mockMvc.perform(put("/users/20/update")
				.param("password", user.getPassword())
				.param("email", mail)
				.param("nickname", jerry))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
	}

	@DisplayName("유저 프로필 세팅 - 실패(형식 오류)")
	@ParameterizedTest
	@CsvSource({"jerry@email.com,j", "jerry,jerry"})
	void setUserProfileFailedByType(String email, String nickname) throws Exception {
		User user = saveAndGetUserJack();
		mockMvc.perform(put("/users/" + user.getId() + "/update")
				.param("password", user.getPassword())
				.param("email", email)
				.param("nickname", nickname))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/profileUpdate"));
	}

	private User saveAndGetUserJack() {
		User user = new User.Builder(UserRepository.atomicKey.incrementAndGet())
			.email(JACK_EMAIL)
			.nickname("jack")
			.password(JACK_PASSWORD)
			.build();
		userRepository.save(user);
		return user;
	}

	@AfterEach
	void clearRepository() {
		userRepository.clear();
	}
}
