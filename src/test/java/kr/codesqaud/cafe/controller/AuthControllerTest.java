package kr.codesqaud.cafe.controller;

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

	@BeforeEach
	public void setUp() {
		httpSession = new MockHttpSession();
		Session session = new Session("id", "testUser");
		httpSession.setAttribute(Session.LOGIN_USER, session);
	}

	@Test
	@DisplayName("로그인성공시 id와 nickName을 session에 저장한다.")
	void signInTest() throws Exception {
		//given
		SignInRequest signInRequest = new SignInRequest("id", "user");
		UserResponse userResponse = new UserResponse("nickName", "aaa@naver.com", "password", "id");
		given(userService.getUserById(signInRequest.getUserId())).willReturn(userResponse);

		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users/sign-in")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "id")
				.param("password", "password123"))

			//then
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users/sign-in-success/id"));

		MvcResult mvcResult = resultActions.andReturn();
		MockHttpSession resultSession = (MockHttpSession)mvcResult.getRequest().getSession();
		Session session = (Session)resultSession.getAttribute(Session.LOGIN_USER);

		assertAll(
			() -> Assertions.assertThat(session.getId().equals(userResponse.getUserId())).isTrue(),
			() -> Assertions.assertThat(session.getNickName().equals(userResponse.getNickName())).isTrue()
		);
	}

	@Test
	@DisplayName("로그인성공시 로그인 성공창을 보여준다.")
	void singInSuccessTest() throws Exception {
		//given
		UserResponse userResponse = new UserResponse("nickName", "aaa@naver.com", "password123", "id");
		given(userService.getUserById(userResponse.getUserId())).willReturn(userResponse);

		//when
		mockMvc.perform(MockMvcRequestBuilders.get("/users/sign-in-success/id")
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
}