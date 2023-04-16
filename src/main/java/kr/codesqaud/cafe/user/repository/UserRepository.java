package kr.codesqaud.cafe.user.repository;

import java.util.List;
import java.util.NoSuchElementException;

import kr.codesqaud.cafe.user.dto.request.SignUpRequestDTO;
import kr.codesqaud.cafe.user.dto.response.UserResponseDTO;

public interface UserRepository {
	void insert(SignUpRequestDTO dto) throws IllegalArgumentException;

	List<UserResponseDTO> selectAll();

	UserResponseDTO selectByUserId(String userId) throws NoSuchElementException;

	void update(SignUpRequestDTO dto) throws NoSuchElementException;
}
