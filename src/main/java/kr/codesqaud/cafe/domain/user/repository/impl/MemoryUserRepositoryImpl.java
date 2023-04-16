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
		user.setId(user.getId());
		users.put(user.getId(), user);
	}

	public Optional<User> findById(String id) {
		if (users.get(id) == null) {
			return Optional.empty();
		}
		return Optional.of(users.get(id));
	}

	public void update(User user) {
		users.put(user.getId(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(users.values());
	}
}
