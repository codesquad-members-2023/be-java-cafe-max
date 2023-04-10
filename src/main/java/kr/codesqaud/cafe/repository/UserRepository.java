package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    List<User> getAllUsers();

    User getSpecificUser(String userId);
}