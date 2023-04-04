package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    List<User> findAll();

    void update(Long id, User updateUser);
}
