package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;

public interface UserRepository {
	User save(User user);

	User update(long index, User user);

	Optional<User> findByUserID(String userID);

	Optional<User> findByEmail(String email);

	Optional<User> findByNickname(String nickname);

	List<User> findAll();
}
