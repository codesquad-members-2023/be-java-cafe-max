package codesquad.cafe.domain.user.repository;

import codesquad.cafe.domain.user.domain.User;

import java.util.List;
import java.util.Optional;



public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    List<User> findAll();

}
