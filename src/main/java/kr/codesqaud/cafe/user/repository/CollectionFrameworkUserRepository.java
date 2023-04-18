package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.common.repository.CollectionFrameworkRepositoryDummyData;
import kr.codesqaud.cafe.user.domain.User;

@Repository
public class CollectionFrameworkUserRepository implements UserRepository {
	private final TempUserTable userTable;

	public CollectionFrameworkUserRepository() {
		userTable = new TempUserTable();
		CollectionFrameworkRepositoryDummyData dummyData = new CollectionFrameworkRepositoryDummyData();
		dummyData.insertUserDummyData(userTable);
	}

	public void save(User user) throws IllegalArgumentException {
		String userId = user.getUserId();
		for (User exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				throw new IllegalArgumentException("이미 등록된 아이디 입니다.");
			}
		}
		userTable.insert(user);
	}

	public List<User> findAll() {
		return userTable.select();
	}

	public User findByUserId(String userId) throws NoSuchElementException {
		for (User exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				return exgistingUser;
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

	public void modify(User user) throws NoSuchElementException {
		String userId = user.getUserId();
		for (User exgistingUser : userTable.select()) {
			if (exgistingUser.getUserId().equals(userId)) {
				userTable.update(exgistingUser, user);
				return;
			}
		}
		throw new NoSuchElementException("존재하지 않는 유저 입니다.");
	}

}
