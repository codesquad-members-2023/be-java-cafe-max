package kr.codesqaud.cafe.user.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.user.domain.User;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;
import kr.codesqaud.cafe.user.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	/**
	 * 회원 정보 등록하기(회원 가입)
	 * @param user 회원 가입 정보
	 * @throws IllegalArgumentException 이미 등록된 회원 ID을 중복하여 등록한 경우 Exception 발생
	 */
	public void addUser(User user) throws UserIdDuplicateException {
		repository.save(user);
	}

	public User performSignIn(String userId, String password) throws UserDoesNotMatchException {
		try {
			User user = findByUserId(userId);
			if (password.equals(user.getPassword())) {
				return user;
			}
		} catch (UserNotExistException e) {
			throw new UserDoesNotMatchException();
		}
		throw new UserDoesNotMatchException();
	}

	/**
	 * 회원 목록 검색
	 * @return 회원 목록이 담긴 List
	 */
	public List<User> findAllUsers() {
		return repository.findAll();
	}

	/**
	 * 회원 정보 검색
	 * @param userId 검색할 회원 ID
	 * @return ID에 해당하는 회원 정보
	 * @throws NoSuchElementException 존재하지 않는 회원을 검색한 경우 Exception 발생
	 */
	public User findByUserId(String userId) throws UserNotExistException {
		return repository.findByUserId(userId);
	}

	/**
	 * 회원 정보 수정
	 * @param user 수정할 회원 정보
	 * @throws UserDoesNotMatchException 존재하지 않는 회원의 정보를 수정하거나, 비밀번호가 일치하지 않는 경우 Exception 발생
	 */
	public void modifyUser(User user) throws UserDoesNotMatchException {
		repository.modify(user);
	}

}
