package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;

@Repository
public class UserRepository {
	List<User> list = new ArrayList<>();

	public void save(User user) {
		list.add(user);
	}
}
