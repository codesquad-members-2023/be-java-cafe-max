package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;

public interface UserRepository {

	void save(User user);

	List<User> findAll();

	Optional<User> findUserProfile(String id);
}
