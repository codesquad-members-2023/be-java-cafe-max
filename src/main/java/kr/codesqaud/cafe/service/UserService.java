package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;
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

    public UserDTO getUserByUserId(String id){
        return userRepository.findUserById(id).toUserDTO();
    }
    public void updateUserByUserId(ProfileEditDTO profileEditDto){
        userRepository.updateUser(profileEditDto.toUser(),profileEditDto.getOriPassword());
    }
}
