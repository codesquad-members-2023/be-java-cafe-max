package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.Repository.UserRepository;
import kr.codesqaud.cafe.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        checkDuplicateId(user.getEmail());
        userRepository.save(user);
    }

    public User findUserById(String id) {

        final User user = userRepository.findById(id).orElseThrow(
                 () ->  new IllegalArgumentException("해당 유저가 없습니다.")
         );
        return user;
    }

    private void checkDuplicateId(String email) {
        userRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    public List<User> showAllUser() {
        return userRepository.findAllUser();
    }
}
