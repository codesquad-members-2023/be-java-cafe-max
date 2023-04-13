package kr.codesqaud.cafe.domain.mapper;

import kr.codesqaud.cafe.controller.dto.user.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.controller.dto.user.UserListDTO;
import kr.codesqaud.cafe.domain.User;

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
        return new User(profileEditDTO.getNickName(), profileEditDTO.getEmail(), profileEditDTO.getNewPassword(), profileEditDTO.getId());
    }
}
