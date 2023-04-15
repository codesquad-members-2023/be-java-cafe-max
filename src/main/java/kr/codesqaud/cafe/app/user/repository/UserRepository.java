package kr.codesqaud.cafe.app.user.repository;

import java.util.List;
import java.util.Optional;
import kr.codesqaud.cafe.app.user.entity.User;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    User save(User user);

    User modify(User user);

    int deleteAll();
}
