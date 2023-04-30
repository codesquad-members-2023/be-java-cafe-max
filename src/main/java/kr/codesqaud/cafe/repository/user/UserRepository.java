package kr.codesqaud.cafe.repository.user;

import java.util.List;
import java.util.Optional;

import kr.codesqaud.cafe.domain.User;

public interface UserRepository {
	User create(User user);

	boolean update(User user);

	Optional<User> findByUserID(String userID);

	List<User> findAll();

	boolean existUserID(String userID);

	boolean existUpdateEmail(String userID, String email);

	boolean existUpdateNickname(String userID, String nickname);
}
