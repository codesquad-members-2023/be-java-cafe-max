package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    List<User> findAll();

    Optional<User> findUserById(String id);

    void updateUser(User User);
}
