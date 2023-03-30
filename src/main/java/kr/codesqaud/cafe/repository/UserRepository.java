package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void save(User user){
        users.add(user);
    }
}
