package kr.codesqaud.cafe.account;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.account.form.JoinForm;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {
	public static final String JACK_EMAIL = "jack@email.com";
	public static final String JACK_PASSWORD = "123456789a";
	public static final String JACK = "jack";
	public static final String JERRY_EMAIL = "jerry@email.com";
	public static final String JERRY = "jerry";
	public static final String EMAIL = "email";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";
	public static final String USER_ID = "userId";
	public static final String PROFILE_SETTING_FORM = "profileSettingForm";
	public static final String PROFILE_FORM = "profileForm";
	public static final String JOIN_FORM = "joinForm";

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserService userService;

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
		int userId = saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param(EMAIL, JACK_EMAIL)
				.param(PASSWORD, JACK_PASSWORD))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/users/" + userId));
	}

	@DisplayName("로그인 - 비밀번호 실패")
	@Test
	void loginFailedByPassword() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param(EMAIL, JACK_EMAIL)
				.param(PASSWORD, "12345678"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/login"));
	}

	@DisplayName("로그인 - 이메일 주소 실패")
	@Test
	void loginFailedByEmail() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(post("/users/login")
				.param(EMAIL, "jack1@email.com")
				.param(PASSWORD, "12345ddd"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/login"));
	}

	@DisplayName("가입 페이지 열람")
	@Test
	void showJoinPage() throws Exception {
		mockMvc.perform(get("/users/join"))
			.andExpect(model().attributeExists(JOIN_FORM))
			.andExpect(status().isOk());
	}

	@DisplayName("유저 추가 - 성공")
	@Test
	void addUserSuccess() throws Exception {
		mockMvc.perform(post("/users")
				.param(EMAIL, JACK_EMAIL)
				.param(NICKNAME, JACK)
				.param(PASSWORD, JACK_PASSWORD))
			.andExpect(status().is3xxRedirection());

		List<User> allMembers = userRepository.getAllMembers();
		System.out.println(allMembers);
		assertThat(userService.findByEmail(JACK_EMAIL)).isPresent();
	}

	@DisplayName("유저 추가 - 실패")
	@ParameterizedTest
	@CsvSource({"sss," + JACK + ",a123456789", JACK_EMAIL + ",j,a1223456789", JACK_EMAIL + "," + JACK + ",123456789"})
	void addUserFailed(String email, String nickname, String password) throws Exception {
		mockMvc.perform(post("/users")
				.param(EMAIL, email)
				.param(NICKNAME, nickname)
				.param(PASSWORD, password))
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
		int userId = saveAndGetUserJack();
		mockMvc.perform(get("/users/" + userId))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists(USER_ID, PROFILE_FORM))
			.andExpect(view().name("account/profile"));
	}

	@DisplayName("유저 프로필 페이지 열람 - 실패")
	@Test
	void showUserFailed() throws Exception {
		mockMvc.perform(get("/users/20"))
			.andExpect(status().is4xxClientError());
	}

	@DisplayName("유저 프로필 수정 페이지 열람  - 성공")
	@Test
	void showUserProfileSuccess() throws Exception {
		int userId = saveAndGetUserJack();
		mockMvc.perform(get("/users/" + userId + "/update"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists(USER_ID, PROFILE_SETTING_FORM))
			.andExpect(view().name("account/profileUpdate"));
	}

	@DisplayName("유저 프로필 수정 페이지 열람  - 실패")
	@Test
	void showUserProfileFailed() throws Exception {
		mockMvc.perform(get("/users/20/update"))
			.andExpect(status().is4xxClientError())
			.andExpect(view().name("error/custom"));
	}

	@DisplayName("유저 프로필 세팅 - 성공")
	@Test
	void setUserProfileSuccess() throws Exception {
		int userId = saveAndGetUserJack();
		String mail = JERRY_EMAIL;
		String jerry = JERRY;
		mockMvc.perform(put("/users/" + userId + "/update")
				.param(PASSWORD, JACK_PASSWORD)
				.param(EMAIL, mail)
				.param(NICKNAME, jerry))
			.andExpect(status().is3xxRedirection());

		User changedUser = userService.findById((long)userId);
		assertThat(changedUser.getEmail()).isEqualTo(mail);
		assertThat(changedUser.getNickname()).isEqualTo(jerry);
	}

	@DisplayName("유저 프로필 세팅 - 실패(비밀번호 불 일치)")
	@Test
	void setUserProfileFailedByPassword() throws Exception {
		int userId = saveAndGetUserJack();
		mockMvc.perform(put("/users/" + userId + "/update")
				.param(PASSWORD, "987654123a")
				.param(EMAIL, JERRY_EMAIL)
				.param(NICKNAME, JERRY))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/profileUpdate"));
	}

	@DisplayName("유저 프로필 세팅 - 실패(유저 아이디)")
	@Test
	void setUserProfileFailedByUserId() throws Exception {
		saveAndGetUserJack();
		mockMvc.perform(put("/users/20/update")
				.param(PASSWORD, JACK_PASSWORD)
				.param(EMAIL, JERRY_EMAIL)
				.param(NICKNAME, JERRY))
			.andExpect(status().is4xxClientError())
			.andExpect(view().name("error/custom"));
	}

	@DisplayName("유저 프로필 세팅 - 실패(형식 오류)")
	@ParameterizedTest
	@CsvSource({JERRY_EMAIL + ",j", JERRY + ",jerry"})
	void setUserProfileFailedByType(String email, String nickname) throws Exception {
		int userId = saveAndGetUserJack();
		mockMvc.perform(put("/users/" + userId + "/update")
				.param(PASSWORD, JACK_PASSWORD)
				.param(EMAIL, email)
				.param(NICKNAME, nickname))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("account/profileUpdate"));
	}

	private int saveAndGetUserJack() {
		JoinForm joinForm = new JoinForm(JACK, JACK_EMAIL, JACK_PASSWORD);
		return userService.createNewUser(joinForm);
	}

}
