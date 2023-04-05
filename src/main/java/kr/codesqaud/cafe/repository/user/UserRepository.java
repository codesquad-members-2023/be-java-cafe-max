package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findByUserId(String userId);
    List<User> findAll();
    boolean isExists(String userId);
    void update(User user);
}
