package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public class JdbcUserRepository implements UserRepository{
    @Override
    public void save(User user) {
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getSpecificUser(String userId) {
        return null;
    }
}
