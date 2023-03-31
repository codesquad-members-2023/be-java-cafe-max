package kr.codesqaud.cafe.domain.user.repository;

import java.util.List;

import kr.codesqaud.cafe.domain.user.entity.User;

public interface UserRepository {

	void save(User user);

	List<User> findAll();

	User findById(Integer id);

	void update(User user);
}
