package kr.codesqaud.cafe.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.dummy.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.exception.UserNotFoundException;

@Repository
public class UserRepository {
	private List<User> users;
	private int userIdx = 1;

	public UserRepository() {
		users = new ArrayList<>();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(users);
	}

	public synchronized void save(SignUpDTO dto) {
		users.add(dto.toUser(userIdx++));
	}

	public List<UserDTO> findAll() {
		return users.stream()
			.map(User::toDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserDTO findByUserId(String userId) throws UserNotFoundException {
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				return user.toDto();
			}
		}
		throw new UserNotFoundException("존재하지 않는 유저 입니다.");
	}

	public void update(SignUpDTO dto) throws UserNotFoundException {
		String userId = dto.getUserId();
		for (User user : users) {
			if (user.getUserId().equals(userId)) {
				user.updateFrom(dto);
				return;
			}
		}
		throw new UserNotFoundException("존재하지 않는 유저 입니다.");
	}

}
