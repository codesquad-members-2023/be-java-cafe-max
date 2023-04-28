package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.user.domain.UserEntity;

@Repository
public class CollectionFrameworkUserRepository implements UserRepository {
	private final TempUserTable userTable;

	public CollectionFrameworkUserRepository() {
		userTable = new TempUserTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(userTable);
	}

	public void save(UserEntity user) {
		userTable.insert(user);
	}

	public List<UserEntity> findAll() {
		return userTable.select();
	}

	public Optional<UserEntity> findByUserId(String userId) {
		return userTable.select()
			.stream()
			.filter(exgistingUser
				-> exgistingUser.getUserId().equals(userId))
			.findAny();
	}

	public boolean update(UserEntity user) {
		String userId = user.getUserId();
		for (UserEntity exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				userTable.update(exgistingUser, user);
				return true;
			}
		}
		return false;
	}

}
