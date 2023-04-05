package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.dummy.CollectionFrameworkRepositoryDummyData;

@Repository
public class UserRepository {
	private final List<User> users;
	private int userIdx = 1;

	public UserRepository() {
		users = new ArrayList<>();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		userIdx = dummyData.insertUserDummyData(users, userIdx);
	}

	public synchronized void insert(SignUpDTO dto) throws IllegalArgumentException {
		String userId = dto.getUserId();
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				throw new IllegalArgumentException("이미 등록된 아이디 입니다.");
			}
		}
		users.add(dto.toUser(userIdx++));
	}

	public List<UserDTO> selectAll() {
		return users.stream()
			.map(User::toDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserDTO selectByUserId(String userId) throws NoSuchElementException {
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				return user.toDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

	public void update(SignUpDTO dto) throws NoSuchElementException {
		String userId = dto.getUserId();
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				user.updateFrom(dto);
				return;
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

}
