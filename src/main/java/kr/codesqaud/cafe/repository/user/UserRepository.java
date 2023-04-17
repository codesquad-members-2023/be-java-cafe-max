package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;

import java.util.List;

public interface UserRepository {
    String save(User user);
    User findByUserId(String userId);
    List<User> findAll();
    boolean exist(String userId);
    int update(User user);
    boolean existByName(String name);
}
