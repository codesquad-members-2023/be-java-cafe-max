package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User join(User user){
        userRepository.save(user);
        return user;
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public User findById(String id){
        return userRepository.findById(id).get();
    }

    public void clearAllUsers(){
        userRepository.clearStore();
    }
}
