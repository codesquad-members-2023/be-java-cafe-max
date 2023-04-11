package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.controller.dto.req.LoginRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.InvalidLoginInfoException;
import kr.codesqaud.cafe.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class AuthService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String login(final LoginRequest request) {
		User savedUser = userRepository.findByUserId(request.getUserId())
			.filter(user -> user.isSamePassword(request.getPassword()))
			.orElseThrow(InvalidLoginInfoException::new);
		return savedUser.getUserId();
	}
}
