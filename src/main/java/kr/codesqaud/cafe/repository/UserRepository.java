package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    void save(User user);

    List<User> findAll();

    User findUserById(String id);

    void updateUser(User User,String oriPassword);
}
