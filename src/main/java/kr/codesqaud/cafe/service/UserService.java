package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.controller.dto.ProfileEditDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.UserRepository;
import kr.codesqaud.cafe.repository.impl.MemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository memoryUserRepository;

    public UserService() {
        this.memoryUserRepository = new MemoryUserRepository();
    }

    public void addUser(User user){
        memoryUserRepository.save(user);
    }

    public List<User> getUserList(){
        return memoryUserRepository.findAll();
    }

    public User getUserByUserId(int userId){
        return memoryUserRepository.findUserById(userId);
    }
    public void updateUserByUserId(ProfileEditDTO profileEditDto,int id){
        memoryUserRepository.updateUser(profileEditDto,id);
    }
}
