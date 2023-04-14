package kr.codesqaud.cafe.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;

public interface UserRepository {
	void insert(SignUpDTO dto) throws IllegalArgumentException;

	List<UserDTO> selectAll();

	UserDTO selectByUserId(String userId) throws NoSuchElementException;

	void update(SignUpDTO dto) throws NoSuchElementException;
}
