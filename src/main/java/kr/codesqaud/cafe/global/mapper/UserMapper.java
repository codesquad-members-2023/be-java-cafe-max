package kr.codesqaud.cafe.global.mapper;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.dto.ProfileEditDTO;
import kr.codesqaud.cafe.account.dto.UserDTO;
import kr.codesqaud.cafe.account.dto.UserListDTO;

public class UserMapper {
	public UserDTO toUserDTO(User user) {
		return new UserDTO(user.getNickName(), user.getEmail(), user.getPassword(), user.getId());
	}

	public UserListDTO toUserListDTO(User user) {
		return new UserListDTO(user.getNickName(), user.getEmail(), user.getId(), user.getDate());
	}

	public User toUser(UserDTO userDTO) {
		return new User(userDTO.getNickName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getId());
	}

	public User toUser(ProfileEditDTO profileEditDTO) {
		return new User(profileEditDTO.getNickName(), profileEditDTO.getEmail(), profileEditDTO.getNewPassword(),
			profileEditDTO.getId());
	}
}
