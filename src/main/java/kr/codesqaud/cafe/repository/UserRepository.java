package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    User save(User user);
    User findByUserId(String userId);
    List<User> findAll();
}
