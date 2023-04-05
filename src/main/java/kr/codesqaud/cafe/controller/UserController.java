package kr.codesqaud.cafe.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.dto.UserDTO;
import kr.codesqaud.cafe.exception.UserNotFoundException;
import kr.codesqaud.cafe.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/signup")
	public String signup(@RequestParam @Nullable List<String> errorMessages, Model model) {
		if (errorMessages != null && !errorMessages.isEmpty()) {
			model.addAttribute("errorMessages", errorMessages);
		}
		return "/user/form";
	}

	@GetMapping("")
	public String userList(Model model) {
		model.addAttribute("users", service.findAllUsers());
		return "/user/list";
	}

	@PostMapping("")
	public String userAdd(@Valid SignUpDTO dto, BindingResult result, RedirectAttributes redirect,
		HttpServletRequest request) {
		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream()
				.map(e -> e.getDefaultMessage())
				.collect(Collectors.toUnmodifiableList());

			redirect.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:/users/signup";
		}

		try {
			service.addUser(dto);
		} catch (IllegalArgumentException e) {
			redirect.addFlashAttribute("errorMessage", e.getMessage());
			return "redirect:" + request.getHeader("Referer");
		}
		return "redirect:/users";
	}

	@GetMapping("/{userId}")
	public String userDetails(@PathVariable String userId, Model model) {
		UserDTO userDto = null;
		try {
			userDto = service.findUser(userId);
		} catch (UserNotFoundException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		model.addAttribute("userDto", userDto);
		return "user/profile";
	}

	@GetMapping("/{userId}/modify-form")
	public String modifyForm(@PathVariable String userId, Model model) {
		UserDTO userDto = null;
		try {
			userDto = service.findUser(userId);
		} catch (UserNotFoundException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		model.addAttribute("userDto", userDto);
		return "user/modify-form";
	}

	@PutMapping("/{userId}")
	public String userModify(@PathVariable String userId, @Valid SignUpDTO dto, BindingResult result,
		RedirectAttributes redirect,
		HttpServletRequest request) {

		if (!userId.equals(dto.getUserId())) {
			redirect.addFlashAttribute("errorMessage", "잘못된 입력입니다.");
			return "redirect:" + request.getHeader("Referer");
		}

		if (result.hasErrors()) {
			List<String> errorMessages = result.getFieldErrors().stream()
				.map(e -> e.getDefaultMessage())
				.collect(Collectors.toUnmodifiableList());

			redirect.addFlashAttribute("errorMessages", errorMessages);
			return "redirect:" + request.getHeader("Referer");
		}

		try {
			service.modifyUser(dto);
		} catch (UserNotFoundException e) {
			redirect.addAttribute("errorMessage", e.getMessage());
		}
		return "redirect:/users/" + dto.getUserId();
	}
}
