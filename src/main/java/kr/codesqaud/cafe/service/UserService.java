package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.BasicUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final BasicUserRepository userRepository;

    public UserService(BasicUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.getUserByUserId(userId).get(); //
    }   // 원래 Optional 아닌데 인터페이스 때문에 어쩔 수 없이 Optional이 되버림 -> fix 하게 될 듯
}
