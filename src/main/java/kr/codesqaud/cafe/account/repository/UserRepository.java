package kr.codesqaud.cafe.account.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.account.domain.User;

public interface UserRepository {
	void save(User user);

	List<User> findAll();

	Optional<User> findUserById(String userId);

	boolean exist(String userId);

	void updateUser(User User);
}
