package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

public interface UserRepository {
	User create(User user);

	boolean update(UserDto userDto);

	Optional<User> findByUserID(String userID);

	Optional<User> findByEmail(String email);

	Optional<User> findByNickname(String nickname);

	List<User> findAll();

	boolean existUserID(String userID);

	boolean existEmail(String email);

	boolean existNickname(String nickname);
}
