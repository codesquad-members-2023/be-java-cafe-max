package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.exception.IdDuplicatedException;
import kr.codesqaud.cafe.exception.InvalidPasswordException;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserDTO userDTO) {
        validateId(userDTO.getId());
        userRepository.save(userDTO.toUser());
    }

    private void validateId(String id) {
        if(userRepository.exist(id)){
            throw new IdDuplicatedException();
        }
    }

    public List<UserListDTO> getUserList() {
        return userRepository.findAll().stream()
                .map(user -> user.toUserListDTO())
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findUserById(id)
                .map(User::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException());
    }

    public void updateUserById(ProfileEditDTO profileEditDto) {
        UserDTO userDto = getUserById(profileEditDto.getId());

        if (!matchPassword(profileEditDto, userDto)){
            throw new InvalidPasswordException(profileEditDto.getId());
        }

        userRepository.updateUser(profileEditDto.toUser());
    }

    private boolean matchPassword(ProfileEditDTO profileEditDto, UserDTO userDto) {
        return userDto.getPassword().equals(profileEditDto.getOriPassword());
    }
}
