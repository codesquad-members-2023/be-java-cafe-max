package kr.codesqaud.cafe.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import kr.codesqaud.cafe.controller.dto.UserParam;
import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.controller.dto.req.ProfileEditRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.NoAuthorizationException;
import kr.codesqaud.cafe.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@DisplayName("[POST] 회원가입 - 정상호출")
	@Test
	void givenJoinInfo_whenJoin_thenRedirectsUserListPage() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("userId", "bruni");
		body.add("password", "qwer1234!");
		body.add("name", "브루니");
		body.add("email", "bruni@codesqaud.com");

		willDoNothing().given(userService).join(any(JoinRequest.class));

		// when & then
		mockMvc.perform(post("/user/create")
							.params(body))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/users"))
			.andDo(print());

		then(userService).should().join(any(JoinRequest.class));
	}

	@DisplayName("[GET] 유저 목록 - 정상호출")
	@Test
	void givenNothing_whenShowAllUsers_thenReturnsUserListView() throws Exception {
		// given
		UserParam userParam = UserParam.from(new User("bruni", "qwer1234!", "브루니", "bruni@codesquad.com"));
		given(userService.getUsers()).willReturn(List.of(userParam));

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/users"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"))
			.andExpect(model().attributeExists("users"))
			.andExpect(model().attribute("users", List.of(userParam)))
			.andDo(print());

		then(userService).should().getUsers();
	}

	@DisplayName("[GET] 유저 프로필 화면 - 정상호출")
	@Test
	void givenNothing_whenShowProfile_thenReturnsProfileView() throws Exception {
		// given
		UserParam userParam = UserParam.from(new User("bruni", "qwer1234!", "브루니", "bruni@codesquad.com"));
		given(userService.findByUserId(anyString())).willReturn(userParam);

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/users/bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/profile"))
			.andExpect(model().attribute("user", userParam))
			.andDo(print());

		then(userService).should().findByUserId("bruni");
	}

	@DisplayName("[GET] 회원정보 수정 화면 - 정상호출")
	@Test
	void givenNothing_whenShowProfileEdit_thenReturnsEditFormView() throws Exception {
		// given
		willDoNothing().given(userService).validateHasAuthorization(anyString(), anyString());

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/users/bruni/form")
							.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/edit_form"))
			.andExpect(model().attribute("userId", "bruni"))
			.andDo(print());

		then(userService).should().validateHasAuthorization("bruni", "bruni");
	}

	@DisplayName("[GET] 회원정보 수정 화면 - 세션 userId와 수정하려는 회원 userId가 일치하지 않을 때")
	@Test
	void givenNotEqualUserId_whenShowProfileEdit_thenReturnsErrorView() throws Exception {
		// given
		willThrow(NoAuthorizationException.class).given(userService).validateHasAuthorization(anyString(), anyString());

		// when & then
		mockMvc.perform(request(HttpMethod.GET, "/users/bruni/form")
							.sessionAttr("sessionedUser", "unknown"))
			.andExpect(status().isOk())
			.andExpect(view().name("error"))
			.andDo(print());

		then(userService).should().validateHasAuthorization("unknown", "bruni");
	}

	@DisplayName("[PUT] 회원정보 수정 - 정상호출")
	@Test
	void givenProfileEditInfo_whenEditProfile_thenRedirectsUserListPage() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("originalPassword", "originPwd");
		body.add("newPassword", "newPwd");
		body.add("name", "브루니");
		body.add("email", "bruni@codesqaud.com");

		willDoNothing().given(userService).validateHasAuthorization(anyString(), anyString());
		willDoNothing().given(userService).editUserProfile(anyString(), any(ProfileEditRequest.class));

		// when & then
		mockMvc.perform(put("/users/bruni")
							.params(body)
							.sessionAttr("sessionedUser", "bruni"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/users"))
			.andDo(print());

		then(userService).should().validateHasAuthorization("bruni", "bruni");
		then(userService).should().editUserProfile(anyString(), any(ProfileEditRequest.class));
	}

	@DisplayName("[PUT] 회원정보 수정 - 세션의 userId와 수정하려는 회원의 userId가 일치하지 않을 때")
	@Test
	void givenNotEqualUserId_whenEditProfile_thenReturnsErrorView() throws Exception {
		// given
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("originalPassword", "originPwd");
		body.add("newPassword", "newPwd");
		body.add("name", "브루니");
		body.add("email", "bruni@codesqaud.com");

		willThrow(NoAuthorizationException.class).given(userService).validateHasAuthorization(anyString(), anyString());
		willDoNothing().given(userService).editUserProfile(anyString(), any(ProfileEditRequest.class));

		// when & then
		mockMvc.perform(put("/users/bruni")
							.params(body)
							.sessionAttr("sessionedUser", "unknown"))
			.andExpect(status().isOk())
			.andExpect(view().name("error"))
			.andDo(print());

		then(userService).should().validateHasAuthorization("unknown", "bruni");
		then(userService).should(times(0)).editUserProfile(anyString(), any(ProfileEditRequest.class));
	}
}
