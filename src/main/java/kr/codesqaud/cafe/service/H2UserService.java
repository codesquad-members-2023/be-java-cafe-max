package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.repository.H2UserRepository;

@Service
@Primary
public class H2UserService implements UserService {
	private final H2UserRepository repository;

	public H2UserService(H2UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public void addUser(SignUpDTO dto) throws IllegalArgumentException {
		repository.insert(dto);
	}

	@Override
	public List<UserDTO> findAllUsers() {
		return null;
	}

	@Override
	public UserDTO findUser(String userId) throws NoSuchElementException {
		return null;
	}

	@Override
	public void modifyUser(SignUpDTO dto) throws NoSuchElementException {

	}
}
