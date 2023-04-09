package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.dto.SignUpFormDto;
import kr.codesqaud.cafe.dto.UpdateFormDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpFormDto dto) {
        User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        userRepository.save(user);
    }

    public User findById(String id) {

        return userRepository.findById(id);
    }


    public void update(User user, UpdateFormDto dto) {
        User current = user;
        if (current.checkPassword( dto)) {
            userRepository.update(new User(user.getUserId(), dto.getNewPassword(), dto.getName(), dto.getEmail()));
        }

    }




    public List<User> getUserList() {
        return userRepository.findAll();
    }
}