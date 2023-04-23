package kr.codesqaud.cafe.domain.user.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

public class MemoryUserRepositoryImpl implements UserRepository {

	private Map<String, User> users = new ConcurrentHashMap<>();

	public void save(User user) {
		users.put(user.getUsername(), user);
	}

	public Optional<User> findById(String username) {
		return Optional.ofNullable(users.get(username));
	}

	public void update(User user) {
		users.put(user.getUsername(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(users.values());
	}
}
