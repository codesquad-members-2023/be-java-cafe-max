package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;

@Repository
public class UserRepository {
	private final List<User> list = new ArrayList<>();

	public void save(User user) {
		list.add(user);
	}

	public List<User> findAll() {
		return list.stream().collect(Collectors.toUnmodifiableList());
	}
}
