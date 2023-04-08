package kr.codesqaud.cafe.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private final UserService service;

	public UserController(UserService service) {
		this.service = service;
	}

	/**
	 * 회원가입 페이지로 이동
	 * @param model 회원가입 정보 유효성 검사 실패시 에러 메시지를 전달하기 위한 model
	 * @return 회원가입 페이지
	 */
	@GetMapping("/signup")
	public String signup(Model model) {
		return "user/form";
	}

	/**
	 * 회원 목록 페이지로 이동
	 * @param model 회원 목록을 전달하기 위한 model
	 * @return 회원 목록 페이지
	 */
	@GetMapping
	public String userList(Model model) {
		model.addAttribute("users", service.findAllUsers());
		return "user/list";
	}

	/**
	 * 회원가입 기능
	 * @param dto 회원가입 정보를 담고 있는 dto
	 * @param result 회원가입 정보 유효성 검사 결과를 담고 있는 객체
	 * @param redirect 회원가입 실패시 회원가입 페이지로 에러메시지를 redirect 하기 위한 model
	 * @return 회원가입 성공시 회원 목록 보기 페이지, 실패시 회원가입 페이지로 redirect
	 */
	@PostMapping
	public String userAdd(@Valid SignUpDTO dto, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			addAttributeErrorMessages(redirect, collectErrorMessages(result));
			return "redirect:/users/signup";
		}
		service.addUser(dto);
		return "redirect:/users";
	}

	/**
	 * 회원 프로필 보기 페이지로 이동
	 * @param userId 회원 아이디
	 * @param errorMessage 존재하지 않는 회원을 조회했을 때 받아올 에러 메시지
	 * @param model 존재하지 않는 회원을 조회했을 때 에러 메시지를 보내기 위한 model
	 * @return 회원 프로필 보기 페이지
	 */
	@GetMapping("/{userId}")
	public String userDetails(@PathVariable String userId, @ModelAttribute("errorMessage") String errorMessage,
		Model model) {
		if (errorMessage.isBlank()) {
			model.addAttribute("userDto", service.findUser(userId));
		}

		return "user/profile";
	}

	/**
	 * 회원 정보 수정 페이지로 이동
	 * @param userId 회원 아이디
	 * @param errorMessage 존재하지 않는 회원을 조회했을 때 받아올 에러 메시지
	 * @param model 존재하지 않는 회원을 조회했을 때 에러 메시지를 보내기 위한 model
	 * @return 회원 정보 수정 페이지
	 */
	@GetMapping("/{userId}/modify-form")
	public String modifyForm(@PathVariable String userId, @ModelAttribute("errorMessage") String errorMessage,
		Model model) {
		if (errorMessage.isBlank()) {
			model.addAttribute("userDto", service.findUser(userId));
		}

		return "user/modify-form";
	}

	/**
	 * 회원 정보 수정 기능
	 * @param userId 회원 아이디
	 * @param dto 수정된 회원 정보를 가지고 있는 dto
	 * @param result dto 유효성 검증 결과를 담고 있는 객체
	 * @param redirect 회원 정보 변경 실패시 전 페이지로 에러 메시지를 보내주기 위한 model
	 * @param request 이전 페이지로 이동할 때 필요한 정보를 얻기 위해 추가
	 * @return 회원 정보 수정 성공시 회원 프로필 페이지, 실패시 이전 페이지로 redirect
	 */
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

		service.modifyUser(dto);

		return "redirect:/users/" + dto.getUserId();
	}

	/**
	 * 이전 페이지로 이동
	 * @param request 이전 페이지로 이동할 때 필요한 정보를 얻기 위해 추가
	 * @return 이전 페이지로 이동할 수 있는 URL 경로
	 */
	private String redirectBack(HttpServletRequest request) {
		return "redirect:" + request.getHeader("Referer");
	}

	/**
	 * 에러 메시지를 model 에 담아 templates 페이지로 보내주는 메서드
	 * @param model 에러 메시지를 전달하기 위한 model
	 * @param errorMessage model 에 담아 보내줄 에러 메시지
	 */
	private void addAttributeErrorMessage(Model model, String errorMessage) {
		if (model instanceof RedirectAttributes) {
			((RedirectAttributes)model).addFlashAttribute("errorMessage", errorMessage);
			return;
		}
		model.addAttribute("errorMessage", errorMessage);
	}

	/**
	 * 에러 메시지 목록을 model 에 담아 templates 페이지로 보내주는 메서드
	 * @param model 에러 메시지 목록을 전달하기 위한 model
	 * @param errorMessages model 에 담아 보내줄 에러 메시지 목록
	 */
	private void addAttributeErrorMessages(Model model, List<String> errorMessages) {
		if (model instanceof RedirectAttributes) {
			((RedirectAttributes)model).addFlashAttribute("errorMessages", errorMessages);
			return;
		}
		model.addAttribute("errorMessages", errorMessages);
	}

	/**
	 * 유효성 검증 결과를 에러 메시지 목록으로 변환
	 * @param result 유효성 검증 결과
	 * @return 에러 메시지 목록
	 */
	private List<String> collectErrorMessages(BindingResult result) {

		return result.getFieldErrors().stream()
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
