package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.utils.SessionTestUtils.*;
import static kr.codesqaud.cafe.utils.UserTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kr.codesqaud.cafe.account.AuthController;
import kr.codesqaud.cafe.account.UserService;
import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.global.config.Session;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	private MockHttpSession httpSession;

	private UserResponse userResponse;

	private SignInRequest signInRequest;

	private static final String NICK_NAME = "nickName";
	private static final String EMAIL = "test@Email.com";
	private static final String PASSWORD = "password123";
	private static final String USER_ID = "tester";

	@BeforeEach
	public void setUp() {
		httpSession = createMockHttpSession();
		userResponse = createUserResponse();
		signInRequest = createSignInRequestWithCorrectPassword();
	}

	@Test
	@DisplayName("회원가입시 db에 회원을 저장한후 user/list 로 리다이렉트한다.")
	void signUpTest() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-up")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickName", NICK_NAME)
				.param("email", EMAIL)
				.param("password", PASSWORD)
				.param("userId", USER_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users/list"));
	}

	@Test
	@DisplayName("로그인성공시 id와 nickName을 session에 저장한다.")
	void signInTest() throws Exception {
		//given
		given(userService.getUserById(signInRequest.getUserId())).willReturn(userResponse);

		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-in")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("userId", USER_ID)
				.param("password", PASSWORD))

			//then
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users/sign-in-success"));

		MvcResult mvcResult = resultActions.andReturn();
		MockHttpSession resultSession = (MockHttpSession)mvcResult.getRequest().getSession();
		Session session = (Session)resultSession.getAttribute(Session.LOGIN_USER);

		assertAll(
			() -> Assertions.assertThat(isEqualsWithSessionIdAndSignInRequestId(userResponse, session)).isTrue(),
			() -> Assertions.assertThat(isEqualsWithSessionIdAndSignInRequestNickName(userResponse, session)).isTrue()
		);
	}

	@Test
	@DisplayName("로그인성공시 로그인 성공창을 보여준다.")
	void singInSuccessTest() throws Exception {
		//given
		given(userService.getUserById(any(String.class))).willReturn(userResponse);

		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/users/sign-in-success")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(httpSession))
			//then
			.andExpect(status().isOk())
			.andExpect(view().name("user/sign-in-success"))
			.andExpect(model().attribute("user", userResponse));
	}

	@Test
	@DisplayName("로그아웃시 세션을 invalidate 시킨다.")
	void signOut() throws Exception {
		//when
		mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-out")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.session(httpSession))
			//then
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));

		Assertions.assertThat(httpSession.isInvalid()).isTrue();
	}

	private static boolean isEqualsWithSessionIdAndSignInRequestNickName(UserResponse userResponse, Session session) {
		return session.getNickName().equals(userResponse.getNickName());
	}

	private static boolean isEqualsWithSessionIdAndSignInRequestId(UserResponse userResponse, Session session) {
		return session.getId().equals(userResponse.getUserId());
	}
}