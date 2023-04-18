package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.user.domain.User;

public interface UserRepository {
	void save(User user) throws IllegalArgumentException;

	List<User> findAll();

	User findByUserId(String userId) throws NoSuchElementException;

	void modify(User user) throws NoSuchElementException;
}
