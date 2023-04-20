package kr.codesqaud.cafe.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

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

import kr.codesqaud.cafe.account.UserController;
import kr.codesqaud.cafe.account.UserService;
import kr.codesqaud.cafe.account.dto.UserListResponse;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.global.config.Session;

@WebMvcTest(UserController.class)
class UserControllerTest {

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
	@DisplayName("회원가입 성공시 회원들의 list 를 user/list 에서 나열한다.")
	void userListTest() throws Exception {
		//given
		List<UserListResponse> userList = new ArrayList<>();
		given(userService.getUserList()).willReturn(userList);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/users/list")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"))
			.andExpect(model().attributeExists("users"))//객체 검증
			.andExpect(model().attribute("users", userList));
	}

	@Test
	@DisplayName("유저의 id,nickName,email정보를 db로부터 가져와 user/profile에서 볼수있다.")
	void userProfileTest() throws Exception {
		//given
		UserResponse userResponse = new UserResponse("nickName", "aaa@naver.com", "password123",
			"testId");
		given(userService.getUserById("testId")).willReturn(userResponse);

		//when & then
		mockMvc.perform(MockMvcRequestBuilders.get("/users/testId")
				.session(httpSession))
			.andExpect(status().isOk())
			.andExpect(view().name("user/profile"))
			.andExpect(model().attributeExists("user"))//객체 검증
			.andExpect(model().attribute("user", userResponse));

	}

	@Test
	@DisplayName("유저의 정보를 수정하면 db에 정보를 업데이트후 세션값을 바꾸고 멤버 리스트로 리다이렉트 한다.")
	void updateTest() throws Exception {

		//given & when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/testId")
				.session(httpSession)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickName", "nickName")
				.param("email", "aaa@naver.com")
				.param("newPassword", "password123")
				.param("oriPassword", "password1234")
				.param("id", "testId"))

			//then
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/users/list"));

		MvcResult mvcResult = resultActions.andReturn();
		MockHttpSession resultSession = (MockHttpSession)mvcResult.getRequest().getSession();
		Session session = (Session)resultSession.getAttribute(Session.LOGIN_USER);

		assertAll(
			() -> Assertions.assertThat(session.getId().equals("testId")).isTrue(),
			() -> Assertions.assertThat(session.getNickName().equals("nickName")).isTrue()
		);
	}
}