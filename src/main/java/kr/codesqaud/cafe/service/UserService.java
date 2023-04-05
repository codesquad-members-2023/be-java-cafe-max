package kr.codesqaud.cafe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository repository;

	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public void addUser(SignUpDTO dto) throws IllegalArgumentException {
		repository.insert(dto);
	}

	public List<UserDTO> findAllUsers() {
		return repository.selectAll();
	}

	public UserDTO findUser(String userId) throws UserNotFoundException {
		return repository.selectByUserId(userId);
	}

	public void modifyUser(SignUpDTO dto) throws UserNotFoundException {
		repository.update(dto);
	}

}
