package kr.codesqaud.cafe.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.support.DefaultMessageSourceResolvable;
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
import kr.codesqaud.cafe.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/signup")
	public String signup(@RequestParam @Nullable List<String> errorMessages, Model model) {
		if (errorMessages != null && !errorMessages.isEmpty()) {
			addAttributeErrorMessages(model, errorMessages);
		}
		return "user/form";
	}

	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", service.findAllUsers());
		return "user/list";
	}

	@PostMapping
	public String userAdd(@Valid SignUpDTO dto, BindingResult result, RedirectAttributes redirect,
		HttpServletRequest request) {
		if (result.hasErrors()) {
			addAttributeErrorMessages(redirect, collectErrorMessages(result));
			return "redirect:/users/signup";
		}

		try {
			service.addUser(dto);
		} catch (IllegalArgumentException e) {
			addAttributeErrorMessage(redirect, e.getMessage());
			return redirectBack(request);
		}
		return "redirect:/users";
	}

	@GetMapping("/{userId}")
	public String userDetails(@PathVariable String userId, Model model) {
		UserDTO userDto = null;
		try {
			userDto = service.findUser(userId);
		} catch (NoSuchElementException e) {
			addAttributeErrorMessage(model, e.getMessage());
		}
		model.addAttribute("userDto", userDto);
		return "user/profile";
	}

	@GetMapping("/{userId}/modify-form")
	public String modifyForm(@PathVariable String userId, Model model) {
		UserDTO userDto = null;
		try {
			userDto = service.findUser(userId);
		} catch (NoSuchElementException e) {
			addAttributeErrorMessage(model, e.getMessage());
		}
		model.addAttribute("userDto", userDto);
		return "user/modify-form";
	}

	@PutMapping("/{userId}")
	public String userModify(@PathVariable String userId, @Valid SignUpDTO dto, BindingResult result,
		RedirectAttributes redirect,
		HttpServletRequest request) {

		if (!userId.equals(dto.getUserId())) {
			addAttributeErrorMessage(redirect, "잘못된 입력입니다.");
			return redirectBack(request);
		}

		if (result.hasErrors()) {
			addAttributeErrorMessages(redirect, collectErrorMessages(result));
			return redirectBack(request);
		}

		try {
			service.modifyUser(dto);
		} catch (NoSuchElementException e) {
			addAttributeErrorMessage(redirect, e.getMessage());
		}
		return "redirect:/users/" + dto.getUserId();
	}

	private String redirectBack(HttpServletRequest request) {
		return "redirect:" + request.getHeader("Referer");
	}

	private void addAttributeErrorMessage(Model model, String errorMessage) {
		if (model instanceof RedirectAttributes) {
			((RedirectAttributes)model).addFlashAttribute("errorMessage", errorMessage);
			return;
		}
		model.addAttribute("errorMessage", errorMessage);
	}

	private void addAttributeErrorMessages(Model model, List<String> errorMessages) {
		if (model instanceof RedirectAttributes) {
			((RedirectAttributes)model).addFlashAttribute("errorMessages", errorMessages);
			return;
		}
		model.addAttribute("errorMessages", errorMessages);
	}

	private List<String> collectErrorMessages(BindingResult result) {
		return result.getFieldErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
