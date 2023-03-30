package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;

@Repository
public class UserRepository {
	private final Map<String, User> userRepository = new HashMap<>();

	public void save(User user) {
		userRepository.put(user.getUserId(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(userRepository.values()).stream().collect(Collectors.toUnmodifiableList());
	}

	public Optional<User> findUserProfile(String id) {
		return Optional.ofNullable(userRepository.get(id));
	}
}
