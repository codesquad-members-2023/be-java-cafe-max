package kr.codesqaud.cafe.repository.temp_memory_db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SignUpDTO;

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
