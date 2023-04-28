package kr.codesqaud.cafe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.account.dto.SignInRequest;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.account.dto.UserResponseForList;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;

public class UserTestUtils {

	private static final String NICK_NAME = "nickName";
	private static final String EMAIL = "test@Email.com";
	private static final String PASSWORD = "password123";
	private static final String USER_ID = "tester";
	private static final String DATE = "2023-4-27";

	public static User createUser() {
		return new User(NICK_NAME, EMAIL, PASSWORD, USER_ID);
	}

	public static UserSignUpRequest createUserSignUpRequest() {
		return new UserSignUpRequest(NICK_NAME, EMAIL, PASSWORD, USER_ID);
	}

	public static UserResponse createUserResponse() {
		return new UserResponse(NICK_NAME, EMAIL, PASSWORD, USER_ID);
	}

	public static UserResponseForList createUserResponseForList() {
		return new UserResponseForList(NICK_NAME, EMAIL, USER_ID, DATE);
	}

	public static List<User> createUsers() {
		return new ArrayList<>(Arrays.asList(createUser()));
	}

	public static ProfileEditRequest createProfileEditRequestWithSameOriPassword() {
		return new ProfileEditRequest(NICK_NAME, EMAIL, PASSWORD, PASSWORD, USER_ID);
	}

	public static ProfileEditRequest createProfileEditRequestWithDifferentOriPassword() {
		return new ProfileEditRequest(NICK_NAME, EMAIL, PASSWORD, PASSWORD + "different", USER_ID);
	}

	public static SignInRequest createSignInRequestWithCorrectPassword() {
		return new SignInRequest(USER_ID, PASSWORD);
	}

	public static SignInRequest createSignInRequestWithInCorrectPassword() {
		return new SignInRequest(USER_ID, PASSWORD + "different");
	}

}
