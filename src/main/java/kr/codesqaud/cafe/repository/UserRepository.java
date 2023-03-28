package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.user.User;

public interface UserRepository {

	Optional<User> save(User user);

	List<User> findAll();

	Optional<User> findByUserId(String userId);
}
