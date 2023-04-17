package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String userId);
    Optional<User> findByName(String name);
    List<User> findAll();
    public void updateUser(User user);

}
