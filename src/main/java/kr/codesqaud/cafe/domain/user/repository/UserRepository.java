package kr.codesqaud.cafe.domain.user.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.user.entity.User;

public interface UserRepository {

	void save(User user);

	List<User> findAll();

	Optional<User> findById(String loginId);

	void update(User user);
}
