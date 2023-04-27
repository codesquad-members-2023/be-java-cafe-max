package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.repository.JdbcUserRepository;
//import kr.codesqaud.cafe.repository.BasicUserRepository;
//import kr.codesqaud.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final JdbcUserRepository userRepository;

    public UserService(
//            @Qualifier("JdbcArticleRepository")
                       JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.getUserList();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id).get(); //
    }   // 원래 Optional 아닌데 인터페이스 때문에 어쩔 수 없이 Optional이 되버림 -> fix 하게 될 듯
}
