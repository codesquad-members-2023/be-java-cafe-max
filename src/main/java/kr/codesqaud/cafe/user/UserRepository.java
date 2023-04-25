package kr.codesqaud.cafe.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String loginId);

    Optional<User> findByName(String name);

    List<User> findAll();
}
