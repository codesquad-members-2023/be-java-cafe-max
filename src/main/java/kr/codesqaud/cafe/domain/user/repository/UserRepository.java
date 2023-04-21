package kr.codesqaud.cafe.domain.user.repository;

import kr.codesqaud.cafe.domain.user.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    void save(User user);

    List<User> findAll();

    Optional<User> findById(String id);

    boolean exist(String name);

    default void update(User user) {
    }


}
