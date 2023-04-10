package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.UserDto;

public class UserFormRepository implements UserRepository {
	private final List<User> userList = new ArrayList<>();

	@Override
	public User save(User user) {
		userList.add(user);
		return user;
	}

	@Override
	public boolean update(UserDto userDto) {
		// userList.set((int)index, user);
		return true;
	}

	@Override
	public Optional<User> findByUserID(String uerID) {
		return userList.stream()
			.filter(user -> user.getUserID().equals(uerID))
			.findAny();
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userList.stream()
			.filter(user -> user.getEmail().equals(email))
			.findAny();
	}

	@Override
	public Optional<User> findByNickname(String nickname) {
		return userList.stream()
			.filter(user -> user.getNickname().equals(nickname))
			.findAny();
	}

	@Override
	public List<User> findAll() {
		return userList.stream().collect(Collectors.toUnmodifiableList());
	}
}
