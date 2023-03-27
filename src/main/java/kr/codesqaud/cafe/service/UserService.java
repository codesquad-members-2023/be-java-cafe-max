package kr.codesqaud.cafe.service;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.controller.dto.req.JoinRequest;
import kr.codesqaud.cafe.domain.user.User;
import kr.codesqaud.cafe.exception.DuplicatedUserIdException;
import kr.codesqaud.cafe.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void join(final JoinRequest request) {
		User user = User.from(request);
		userRepository.save(user)
			.orElseThrow(() -> new DuplicatedUserIdException("해당 아이디는 이미 존재합니다."));
	}
}
