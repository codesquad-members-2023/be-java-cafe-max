package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;
import kr.codesqaud.cafe.domain.User;
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

    public void addUser(UserDTO userDTO){
        userRepository.save(userDTO.toUser());
    }

    public List<UserListDTO> getUserList(){
        return userRepository.findAll().stream()
                .map(user -> user.toUserListDTO())
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDTO getUserById(String id){
        return userRepository.findUserById(id)
                .map(User::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자가 없습니다."));
    }

    public void updateUserByUserId(ProfileEditDTO profileEditDto){
        UserDTO userDto = getUserById(profileEditDto.getId());

        if(userDto.getPassword().equals(profileEditDto.getOriPassword())){
            userRepository.updateUser(profileEditDto.toUser());
        } else {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
