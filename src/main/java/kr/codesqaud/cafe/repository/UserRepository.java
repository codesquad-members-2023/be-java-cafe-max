package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.dummy.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.repository.temp_memory_db.TempUserTable;

@Repository
public class UserRepository {
	private final TempUserTable userTable;

	public UserRepository() {
		userTable = new TempUserTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(userTable);
	}

	public synchronized void insert(SignUpDTO dto) throws IllegalArgumentException {
		String userId = dto.getUserId();
		for (User user : userTable.select()) {
			if (user.getUserId().equals(userId)) {
				throw new IllegalArgumentException("이미 등록된 아이디 입니다.");
			}
		}
		userTable.insert(dto);
	}

	public List<UserDTO> selectAll() {
		return userTable.select().stream()
			.map(User::toDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserDTO selectByUserId(String userId) throws NoSuchElementException {
		for (User user : userTable.select()) {
			if (user.getUserId().equals(userId)) {
				return user.toDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

	public synchronized void update(SignUpDTO dto) throws NoSuchElementException {
		String userId = dto.getUserId();
		for (User user : userTable.select()) {
			if (user.getUserId().equals(userId)) {
				userTable.update(user, dto);
				return;
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

}
