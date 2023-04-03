package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.controller.dto.UserListDTO;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.repository.impl.MemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository memoryUserRepository;

    public UserService() {
        this.memoryUserRepository = new MemoryUserRepository();
    }

    public void addUser(UserDTO userDTO){
        memoryUserRepository.save(userDTO.toUser());
    }

    public List<UserListDTO> getUserList(){
        return memoryUserRepository.findAll().stream()
                .map(user -> user.toUserListDTO())
                .collect(Collectors.toUnmodifiableList());
    }

    public UserDTO getUserByUserId(String id){
        return memoryUserRepository.findUserById(id).toUserDTO();
    }
    public void updateUserByUserId(ProfileEditDTO profileEditDto){
        memoryUserRepository.updateUser(profileEditDto.toUser(),profileEditDto.getOriPassword());
    }
}
