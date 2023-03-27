package kr.codesqaud.cafe.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.repository.UserRepository;

@Repository
public class UserMemoryRepository implements UserRepository {

	private final Map<String, User> userRepository = new HashMap<>();

	@Override
	public Optional<User> save(User user) {
		if (userRepository.containsKey(user.getUserId())) {
			return Optional.empty();
		}
		userRepository.put(user.getUserId(), user);
		return Optional.of(user);
	}
}
