package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.dto.request.SignUpRequestDTO;
import kr.codesqaud.cafe.user.dto.response.UserResponseDTO;

@Repository
public class CollectionFrameworkUserRepository implements UserRepository {
	private final TempUserTable userTable;

	public CollectionFrameworkUserRepository() {
		userTable = new TempUserTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(userTable);
	}

	public void insert(SignUpRequestDTO dto) throws IllegalArgumentException {
		String userId = dto.getUserId();
		for (User user : userTable.select()) {
			if (user.getUserId().equals(userId)) {
				throw new IllegalArgumentException("이미 등록된 아이디 입니다.");
			}
		}
		userTable.insert(dto);
	}

	public List<UserResponseDTO> selectAll() {
		return userTable.select().stream()
			.map(User::toDto)
			.collect(Collectors.toUnmodifiableList());
	}

	public UserResponseDTO selectByUserId(String userId) throws NoSuchElementException {
		for (User user : userTable.select()) {
			if (user.getUserId().equals(userId)) {
				return user.toDto();
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

	public void update(SignUpRequestDTO dto) throws NoSuchElementException {
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
