package kr.codesqaud.cafe.global.mapper;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.dto.ProfileEditRequest;
import kr.codesqaud.cafe.account.dto.UserListResponse;
import kr.codesqaud.cafe.account.dto.UserResponse;
import kr.codesqaud.cafe.account.dto.UserSignUpRequest;

@Component
public class UserMapper {
	public UserResponse toUserResponse(User user) {
		return new UserResponse(user.getNickName(), user.getEmail(), user.getPassword(), user.getId());
	}

	public UserListResponse toUserListResponse(User user) {
		return new UserListResponse(user.getNickName(), user.getEmail(), user.getId(), user.getDate());
	}

	public User toUser(UserSignUpRequest userSignUpRequest) {
		return new User(userSignUpRequest.getNickName(), userSignUpRequest.getEmail(), userSignUpRequest.getPassword(),
			userSignUpRequest.getId());
	}

	public User toUser(ProfileEditRequest profileEditRequest) {
		return new User(profileEditRequest.getNickName(), profileEditRequest.getEmail(),
			profileEditRequest.getNewPassword(),
			profileEditRequest.getId());
	}
}
