package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> userRepository;

    public UserRepository() {
        this.userRepository = new ArrayList<>();
    }

    public void join(User user) {
        userRepository.add(user);
        user.setUserId(userRepository.size());
    }

    public List<User> findAll() {
        return userRepository;
    }

    public User getUserByUserId(int userId){
        for (User user: userRepository) {
            if(user.getUserId() == userId)
                return user;
        }
        return null;
    }

}
