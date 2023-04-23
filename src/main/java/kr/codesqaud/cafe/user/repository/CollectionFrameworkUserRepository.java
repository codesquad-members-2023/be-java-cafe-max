package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.user.domain.UserEntity;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;

@Repository
public class CollectionFrameworkUserRepository implements UserRepository {
	private final TempUserTable userTable;

	public CollectionFrameworkUserRepository() {
		userTable = new TempUserTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(userTable);
	}

	public void save(UserEntity user) throws UserIdDuplicateException {
		String userId = user.getUserId();
		for (UserEntity exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				throw new UserIdDuplicateException(user.getUserId());
			}
		}
		userTable.insert(user);
	}

	public List<UserEntity> findAll() {
		return userTable.select();
	}

	public UserEntity findByUserId(String userId) throws NoSuchElementException {
		for (UserEntity exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				return exgistingUser;
			}
		}
		throw new NoSuchElementException(userId);
	}

	public void update(UserEntity user) throws UserDoesNotMatchException {
		String userId = user.getUserId();
		for (UserEntity exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				userTable.update(exgistingUser, user);
				return;
			}
		}
		throw new UserDoesNotMatchException();
	}

}
