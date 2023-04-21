package kr.codesqaud.cafe.user.service;

import kr.codesqaud.cafe.user.controller.LoginForm;
import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.repository.UserRepository;
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

    public User join(User user) {
        userRepository.save(user);
        return user;
    }

    public Boolean validationNullUser(Optional<User> user){
        return user.isPresent();
    }


    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findById(String id) throws NullPointerException {
        Optional<User> requestUser = userRepository.findById((id));
        if(!validationNullUser(requestUser)){
            throw new NullPointerException("존재하지 않는 id");
        }
        return requestUser.get();
    }

    public void clearAllUsers() {
        userRepository.clearStore();
    }
}
