package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.LoginDTO;
import kr.codesqaud.cafe.controller.dto.user.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.controller.dto.user.UserListDTO;
import kr.codesqaud.cafe.domain.mapper.UserMapper;
import kr.codesqaud.cafe.exception.user.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.LoginInvalidPasswordException;
import kr.codesqaud.cafe.exception.user.UserUpdateInvalidPasswordException;
import kr.codesqaud.cafe.exception.user.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(@Qualifier("jdbcRepository")UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userMapper = new UserMapper();
    }

    public void addUser(UserDTO userDTO) {
        validateId(userDTO.getId());
        userRepository.save(userMapper.toUser(userDTO));
    }

    private void validateId(String id) {
        if(userRepository.exist(id)){
            throw new AlreadyUserExistenceException();
        }
    }

    public List<UserListDTO> getUserList() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserListDTO)
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findUserById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserDTO updateUserById(ProfileEditDTO profileEditDto) {
        UserDTO userDto = getUserById(profileEditDto.getId());

        if (!matchPassword(profileEditDto, userDto)){
            throw new UserUpdateInvalidPasswordException();
        }

        return userMapper.toUserDTO(userRepository.updateUser(userMapper.toUser(profileEditDto)));
    }

    private boolean matchPassword(ProfileEditDTO profileEditDto, UserDTO userDto) {
        return userDto.getPassword().equals(profileEditDto.getOriPassword());
    }

    public void matchPassword(LoginDTO loginDto){
        UserDTO userdto = getUserById(loginDto.getUserId());
        if(!loginDto.getPassword().equals(userdto.getPassword())){
            throw new LoginInvalidPasswordException();
        }
    }

}
