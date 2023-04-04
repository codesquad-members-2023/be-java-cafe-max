package kr.codesqaud.cafe.repository;

import java.util.List;
import kr.codesqaud.cafe.domain.User;

public interface UserRepository {

    User save(User user);

    User findById(String userId);

    User findByName(String name);

    List<User> findAll();
}
