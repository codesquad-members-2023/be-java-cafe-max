package kr.codesqaud.cafe.user.repository;

import kr.codesqaud.cafe.user.service.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByUserId(String userId);

    List<User> findAll();

    User update(User currUser, User newUser);
}
