package kr.codesqaud.cafe.user.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.user.domain.UserEntity;

public class TempUserTable {
	private final List<UserEntity> users = new ArrayList<>();
	private final AtomicInteger id = new AtomicInteger(1);

	public synchronized void insert(UserEntity user) {
		UserEntity userGivenId = new UserEntity(id.getAndIncrement(), user.getUserId(), user.getPassword(),
			user.getName(),
			user.getEmail());
		users.add(userGivenId);
	}

	public List<UserEntity> select() {
		return Collections.unmodifiableList(users);
	}

	public synchronized void update(UserEntity exgistingUser, UserEntity user) {
		int listIndex = users.indexOf(exgistingUser);
		if (listIndex == -1) {
			return;
		}
		users.get(listIndex).updateFrom(user);
	}

}
