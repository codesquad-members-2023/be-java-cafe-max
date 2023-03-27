package kr.codesqaud.cafe.repository;

import java.util.Optional;

import kr.codesqaud.cafe.domain.user.User;

public interface UserRepository {

	Optional<User> save(User user);
}
