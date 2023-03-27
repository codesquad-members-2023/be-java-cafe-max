package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.Optional;

public interface UserRepository {
    Long save(User user);

    Optional<User> findById(Long id);
}
