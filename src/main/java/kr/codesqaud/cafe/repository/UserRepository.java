package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void join(final User user);
    Optional<User> findById(final long id);
    List<User> findAll();
}
