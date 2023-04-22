package kr.codesqaud.cafe.user.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.user.domain.User;

public class TempUserTable {
	private final List<User> users = new ArrayList<>();
	private final AtomicInteger id = new AtomicInteger(1);

	public synchronized void insert(User user) {
		User userGivenId = new User(id.getAndIncrement(), user.getUserId(), user.getPassword(), user.getName(),
			user.getEmail());
		users.add(userGivenId);
	}

	public List<User> select() {
		return Collections.unmodifiableList(users);
	}

	public synchronized void update(User exgistingUser, User user) {
		int listIndex = users.indexOf(exgistingUser);
		if (listIndex == -1) {
			return;
		}
		users.get(listIndex).updateFrom(user);
	}

}
