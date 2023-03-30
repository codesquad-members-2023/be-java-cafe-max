package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void saveUser(UserForm userForm) {
        User user = new User();
        user.setUserId(userForm.getUserId());
        user.setPassword(userForm.getPassword());
        user.setUserName(userForm.getUserName());
        user.setEmail(userForm.getEmail());

        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.getRepository();
    }
}
