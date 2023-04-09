package kr.codesqaud.cafe;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void join(User user){
        userRepository.save(user);
    }

    public List<User> getUserList = userRepository.getUserList();
}
