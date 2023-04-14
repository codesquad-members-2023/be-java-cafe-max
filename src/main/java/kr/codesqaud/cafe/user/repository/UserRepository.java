package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.user.dto.request.SignUpDTO;
import kr.codesqaud.cafe.user.dto.response.UserDTO;

public interface UserRepository {
	void insert(SignUpDTO dto) throws IllegalArgumentException;

	List<UserDTO> selectAll();

	UserDTO selectByUserId(String userId) throws NoSuchElementException;

	void update(SignUpDTO dto) throws NoSuchElementException;
}
