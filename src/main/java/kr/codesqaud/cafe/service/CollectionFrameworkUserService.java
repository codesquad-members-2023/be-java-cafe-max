package kr.codesqaud.cafe.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.repository.CollectionFrameworkUserRepository;

@Service
public class CollectionFrameworkUserService implements UserService {

	private final CollectionFrameworkUserRepository repository;

	public CollectionFrameworkUserService(CollectionFrameworkUserRepository repository) {
		this.repository = repository;
	}

	/**
	 * 회원 정보 등록하기(회원 가입)
	 * @param dto 회원 가입 정보
	 * @throws IllegalArgumentException 이미 등록된 회원 ID을 중복하여 등록한 경우 Exception 발생
	 */
	public void addUser(SignUpDTO dto) throws IllegalArgumentException {
		repository.insert(dto);
	}

	/**
	 * 회원 목록 검색
	 * @return 회원 목록이 담긴 List
	 */
	public List<UserDTO> findAllUsers() {
		return repository.selectAll();
	}

	/**
	 * 회원 정보 검색
	 * @param userId 검색할 회원 ID
	 * @return ID에 해당하는 회원 정보
	 * @throws NoSuchElementException 존재하지 않는 회원을 검색한 경우 Exception 발생
	 */
	public UserDTO findUser(String userId) throws NoSuchElementException {
		return repository.selectByUserId(userId);
	}

	/**
	 * 회원 정보 수정
	 * @param dto 수정할 회원 정보
	 * @throws NoSuchElementException 존재하지 않는 회원의 정보를 수정하려 한 경우 Exception 발생
	 */
	public void modifyUser(SignUpDTO dto) throws NoSuchElementException {
		repository.update(dto);
	}

}
