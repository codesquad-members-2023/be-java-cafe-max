package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void join(User user);
    Optional<User> findById(long id);
    List<User> findAll();
}
