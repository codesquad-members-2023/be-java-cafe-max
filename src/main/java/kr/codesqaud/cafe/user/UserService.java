package kr.codesqaud.cafe.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository = new UserRepository();

    public void saveUser(UserForm userForm) {
        User user = new User();
        user.setUserId(userForm.getUserId());
        user.setPassword(userForm.getPassword());
        user.setUserName(user.getUserName());
        user.setEmail(user.getEmail());

        userRepository.save(user);
    }
}
