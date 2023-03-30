package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;
import kr.codesqaud.cafe.repository.UserRepository;

import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void addUser(UserDto userDto){
        userRepository.save(userDto);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public UserDto getUserByUserId(int userId){
        return userRepository.findUser(userId);
    }
}
