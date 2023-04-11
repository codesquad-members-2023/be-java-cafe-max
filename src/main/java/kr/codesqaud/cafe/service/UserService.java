package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;
import kr.codesqaud.cafe.domain.mapper.UserMapper;
import kr.codesqaud.cafe.exception.AlreadyUserExistenceException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
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
                .map(user -> userMapper.toUserListDTO(user))
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findUserById(id)
                .map(user -> userMapper.toUserDTO(user))
                .orElseThrow(UserNotFoundException::new);
    }

    public void updateUserById(ProfileEditDTO profileEditDto) {
        UserDTO userDto = getUserById(profileEditDto.getId());

        if (!matchPassword(profileEditDto, userDto)){
            throw new InvalidPasswordException(profileEditDto.getId());
        }

        userRepository.updateUser(userMapper.toUser(profileEditDto));
    }

    private boolean matchPassword(ProfileEditDTO profileEditDto, UserDTO userDto) {
        return userDto.getPassword().equals(profileEditDto.getOriPassword());
    }
}
