package kr.codesqaud.cafe.repository.user;

import kr.codesqaud.cafe.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByLoginId(String loginId);

    List<User> findAll();

    void update(Long id, User user, String existingPassword);
}
