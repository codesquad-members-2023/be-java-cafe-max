package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.domain.User;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String userId);

    Optional<User> findByName(String name);

    List<User> findAll();
}
