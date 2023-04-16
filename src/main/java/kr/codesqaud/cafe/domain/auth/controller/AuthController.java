package kr.codesqaud.cafe.domain.auth.controller;

import java.util.NoSuchElementException;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.auth.dto.request.AuthLoginRequestDto;
import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.global.common.constant.SessionAttributeNames;

@Controller
public class AuthController {

	private final UserRepository userRepository;

	AuthController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/login")
	public String login(AuthLoginRequestDto authLoginRequestDto, HttpSession session) {
		User loginUser = userRepository.findById(authLoginRequestDto.getId())
			.filter(user -> user.isCorrectPassword(authLoginRequestDto.getPassword()))
			.orElseThrow(NoSuchElementException::new);
		session.setAttribute(SessionAttributeNames.LOGIN_USER_ID, loginUser.getId());
		session.setAttribute(SessionAttributeNames.LOGIN_USER_NAME, loginUser.getName());
		return "redirect:/";
	}

	@PostMapping("/logout")
	public String logout(HttpSession session) {
		try {
			Objects.requireNonNull(session).invalidate();
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
		return "rediretc:/";
	}
}
