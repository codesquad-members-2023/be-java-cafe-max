package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.user.domain.UserEntity;

public interface UserRepository {
	void save(UserEntity user);

	List<UserEntity> findAll();

	Optional<UserEntity> findByUserId(String userId);

	boolean update(UserEntity user);
}
