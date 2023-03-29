package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User join(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}
