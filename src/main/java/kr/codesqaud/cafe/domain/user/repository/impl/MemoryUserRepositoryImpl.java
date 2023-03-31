package kr.codesqaud.cafe.domain.user.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;

@Repository
public class MemoryUserRepositoryImpl implements UserRepository {

	private Map<Integer, User> users = new ConcurrentHashMap<>();
	private static Integer autoIncrement = 0;

	public void save(User user) {
		user.setId(++autoIncrement);
		users.put(autoIncrement, user);
	}

	public User findById(Integer id) {
		return users.get(id);
	}

}
