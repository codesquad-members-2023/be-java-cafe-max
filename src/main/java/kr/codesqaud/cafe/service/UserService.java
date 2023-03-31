package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.controller.dto.UserDTO;
import kr.codesqaud.cafe.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void addUser(UserDTO userDto){
        userRepository.save(userDto);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public UserDTO getUserByUserId(int userId){
        return userRepository.findUser(userId);
    }

    public void updateUserByUserId(ProfileEditDTO profileEditDto){
        userRepository.findOne(profileEditDto);
    }

}
