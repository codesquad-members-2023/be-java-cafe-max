package kr.codesqaud.cafe.user.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import kr.codesqaud.cafe.user.controller.request.SignInRequestDTO;
import kr.codesqaud.cafe.user.controller.request.SignUpRequestDTO;
import kr.codesqaud.cafe.user.controller.response.AuthSession;
import kr.codesqaud.cafe.user.controller.response.UserResponseDTO;
import kr.codesqaud.cafe.user.exception.UserDoesNotMatchException;
import kr.codesqaud.cafe.user.exception.UserIdDuplicateException;
import kr.codesqaud.cafe.user.exception.UserNotExistException;
import kr.codesqaud.cafe.user.service.UserService;

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

	@GetMapping("/signin")
	public String signIn(Model model) {
		return "user/signin";
	}

	@PostMapping("/signin")
	public String signIn(SignInRequestDTO dto, HttpServletRequest request, HttpServletResponse response) throws
		UserDoesNotMatchException {
		HttpSession session = request.getSession();
		session.setAttribute("authSession",
			AuthSession.from(service.performSignIn(dto.getUserId(), dto.getPassword())));

		if (dto.isRemember()) {
			Cookie cookie = new Cookie("rememberCheckbox", dto.getUserId());
			cookie.setPath("/");
			cookie.setMaxAge(60 * 60 * 24 * 365);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("rememberCheckbox", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}

		return "redirect:/questions";
	}

	@GetMapping("/signout")
	public String signOut(HttpSession session) {
		session.removeAttribute("authSession");
		return "redirect:/questions";
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
	public String userAdd(@Valid SignUpRequestDTO dto, BindingResult result, RedirectAttributes redirect) throws
		UserIdDuplicateException {
		if (result.hasErrors()) {
			addAttributeErrorMessages(redirect, collectErrorMessages(result));
			return "redirect:/users/signup";
		}
		service.addUser(dto.toEntity());
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
	public String userDetail(@PathVariable String userId, @ModelAttribute("errorMessage") String errorMessage,
		Model model) throws UserNotExistException {
		if (errorMessage.isBlank()) {
			model.addAttribute("userResponseDto", UserResponseDTO.from(service.findByUserId(userId)));
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
		Model model, HttpServletRequest request) throws UserNotExistException {

		if (!checkAuthSession(request.getSession(false), userId)) {
			return "error/403-forbidden";
		}

		if (errorMessage.isBlank()) {
			model.addAttribute("userResponseDto", UserResponseDTO.from(service.findByUserId(userId)));
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
	public String userModify(@PathVariable String userId, @Valid SignUpRequestDTO dto, BindingResult result,
		RedirectAttributes redirect,
		HttpServletRequest request) throws UserDoesNotMatchException {

		if (!userId.equals(dto.getUserId())) {
			addAttributeErrorMessage(redirect, "잘못된 입력입니다.");
			return redirectBack(request);
		}

		if (result.hasErrors()) {
			addAttributeErrorMessages(redirect, collectErrorMessages(result));
			return redirectBack(request);
		}
		service.updateUser(dto.toEntity());

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

	/**
	 * pathValue의 userId와 session의 userId가 같은지 검증한다.
	 * @param httpSession HttpSession
	 * @param userIdFromPath pathValue의 userId
	 * @return session 검증 통과시 ture, 실패시 false 반환
	 */
	private boolean checkAuthSession(HttpSession httpSession, String userIdFromPath) {
		AuthSession authSession = (httpSession != null) ? (AuthSession)httpSession.getAttribute("authSession") : null;
		return httpSession != null && userIdFromPath.equals(authSession.getUserId());
	}
}
