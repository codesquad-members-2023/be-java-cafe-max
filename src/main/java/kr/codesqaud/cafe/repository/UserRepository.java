package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Long save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void update(User toUser);
}
