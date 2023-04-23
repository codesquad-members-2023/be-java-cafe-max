package kr.codesqaud.cafe.user.repository;

import java.util.List;

import kr.codesqaud.cafe.user.domain.UserEntity;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;

public interface UserRepository {
	void save(UserEntity user) throws UserIdDuplicateException;

	List<UserEntity> findAll();

	UserEntity findByUserId(String userId) throws UserNotExistException;

	void update(UserEntity user) throws UserDoesNotMatchException;
}
