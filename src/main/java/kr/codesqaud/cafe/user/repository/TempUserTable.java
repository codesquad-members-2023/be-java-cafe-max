package kr.codesqaud.cafe.user.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.request.SignUpDTO;

public class TempUserTable {
	private final List<User> users = new ArrayList<>();
	private final AtomicInteger userIdx = new AtomicInteger(1);

	public synchronized void insert(SignUpDTO dto) {
		users.add(dto.toUser(userIdx.getAndIncrement()));
	}

	public List<User> select() {
		return Collections.unmodifiableList(users);
	}

	public synchronized void update(User user, SignUpDTO dto) {
		int listIndex = users.indexOf(user);
		if (listIndex == -1) {
			return;
		}
		users.get(listIndex).updateFrom(dto);
	}

}
