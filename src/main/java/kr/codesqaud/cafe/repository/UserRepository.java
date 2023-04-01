package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.user.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    User findById(String id);

    List<User> findAll();
}
