package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public List<User> getUserList(){
        return userRepository.findAll();
    }

    public User getUserByUserId(int userId){
        return userRepository.findUser(userId);
    }
    public void updateUserByUserId(ProfileEditDTO profileEditDto,int id){
        userRepository.findOne(profileEditDto,id);
    }
}
