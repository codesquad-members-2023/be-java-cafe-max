package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;

@Repository
public class UserRepository {
	private final Map<String, User> userInfo = new HashMap<>();

	public void save(User user) {
		userInfo.put(user.getUserId(), user);
	}

	public List<User> findAll() {
		return new ArrayList<>(userInfo.values()).stream().collect(Collectors.toUnmodifiableList());
	}

	public List<User> findUserProfile(String id) {
		List<User> userProfile = new ArrayList<>();
		if (userInfo.containsKey(id)) {
			userProfile.add(userInfo.get(id));
		}
		return userProfile.stream().collect(Collectors.toUnmodifiableList());
	}
}
