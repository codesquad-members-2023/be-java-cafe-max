package kr.codesqaud.cafe.domain.auth.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.auth.dto.request.AuthLoginRequestDto;
import kr.codesqaud.cafe.domain.user.entity.User;
import kr.codesqaud.cafe.domain.user.repository.UserRepository;
import kr.codesqaud.cafe.global.common.constant.SessionAttributeNames;
import kr.codesqaud.cafe.global.exception.user.InvalidLoginInfoException;

@Controller
public class AuthController {

	private final UserRepository userRepository;

	AuthController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/login")
	public String login(AuthLoginRequestDto authLoginRequestDto, HttpSession session) {
		User loginUser = userRepository.findById(authLoginRequestDto.getLoginId())
			.filter(user -> user.matchPassword(authLoginRequestDto.getPassword()))
			.orElseThrow(InvalidLoginInfoException::new);
		session.setAttribute(SessionAttributeNames.LOGIN_USER_ID.type(), loginUser.getLoginId());
		session.setAttribute(SessionAttributeNames.LOGIN_USER_NAME.type(), loginUser.getName());
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		try {
			Objects.requireNonNull(session).invalidate();
		} catch (NullPointerException e) {
			throw new RuntimeException(e);
		}
		return "redirect:/";
	}
}
