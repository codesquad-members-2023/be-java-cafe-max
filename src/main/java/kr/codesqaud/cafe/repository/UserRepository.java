package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;

public interface UserRepository {
	void save(User user);

	List<User> findAll();

	Optional<User> findUserById(String id);

	boolean exist(String id);

	void updateUser(User User);
}
