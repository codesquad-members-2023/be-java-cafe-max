package kr.codesqaud.cafe.user.repository;

import java.util.List;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;

public interface UserRepository {
	void save(User user) throws UserIdDuplicateException;

	List<User> findAll();

	User findByUserId(String userId) throws UserNotExistException;

	void modify(User user) throws UserDoesNotMatchException;
}
