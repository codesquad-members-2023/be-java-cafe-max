package kr.codesqaud.cafe;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void join(User user){
        userRepository.save(user);
    }

    public List<User> getUserList = userRepository.getUserList();
}
