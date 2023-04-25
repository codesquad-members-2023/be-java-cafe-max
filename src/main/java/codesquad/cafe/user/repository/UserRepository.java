package codesquad.cafe.user.repository;

import codesquad.cafe.user.domain.User;

import java.util.List;
import java.util.Optional;



public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

    void update(User user);
}
