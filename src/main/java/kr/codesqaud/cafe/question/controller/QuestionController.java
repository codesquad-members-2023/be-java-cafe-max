package kr.codesqaud.cafe.question.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.common.auth.exception.NoAccessPermissionException;
import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.common.auth.utill.AuthSessionValidator;
import kr.codesqaud.cafe.common.web.PageHandler;
import kr.codesqaud.cafe.question.controller.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionBoardResponseDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionDetailResponseDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionTitleResponseDTO;
import kr.codesqaud.cafe.question.domain.QuestionEntity;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.service.QuestionService;
import kr.codesqaud.cafe.question_comment.controller.response.CommentResponseDTO;
import kr.codesqaud.cafe.question_comment.service.CommentService;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	private final QuestionService questionService;
	private final CommentService commentService;

	public QuestionController(QuestionService questionService, CommentService commentService) {
		this.questionService = questionService;
		this.commentService = commentService;
	}

	/**
	 * Q&A 게시글 작성 페이지로 이동 (접근 권한: 로그인한 모든 유저)
	 * @return Q&A 게시글 작성 페이지
	 */
	@GetMapping("/write-form")
	public String writeForm(HttpSession session) throws NoAuthSessionException {
		AuthSessionValidator.validateUserIsSignedIn(session);
		return "qna/form";
	}

	/**
	 * Q&A 게시글 쓰기 기능. (접근 권한: 로그인한 모든 유저)
	 * @param dto 게시글 쓰기용 dto
	 * @return Q&A 게시글 작성 페이지로 redirect
	 */
	@PostMapping
	public String questionAdd(QuestionWriteRequestDTO dto, HttpSession session) throws NoAuthSessionException {
		AuthSession authSession = AuthSessionValidator.validateUserIsSignedIn(session);
		questionService.save(dto.toEntity(authSession.getId()));

		return "redirect:questions/write-form";
	}

	/**
	 * Q&A 게시글 목록 페이지로 이동
	 * @param page 페이지 번호
	 * @param model `게시글`, `페이징 정보`를 담고 있는 dto 를 전달하기 위한 model
	 * @return Q&A 게시글 목록 페이지
	 */
	@GetMapping
	public String questionList(@RequestParam(value = "page", required = false, defaultValue = "1") long page,
		Model model) {
		PageHandler pageHandler = new PageHandler(questionService.countBy(), page);

		List<QuestionTitleResponseDTO> questionTitles =
			questionService.findPageBy(pageHandler.getPostOffset(), pageHandler.getPageSize())
				.stream()
				.map(QuestionTitleResponseDTO::from)
				.collect(Collectors.toUnmodifiableList());

		model.addAttribute("questionBoardResponseDTO",
			new QuestionBoardResponseDTO(questionTitles, pageHandler));

		return "index";
	}

	/**
	 * Q&A 게시글 상세 보기 페이지로 이동. (접근 권한: 로그인한 모든 유저)
	 * @param id 조회하고자 하는 Q&A 게시글의 id
	 * @param errorMessage 없는 게시글 또는 잘못된 입력값이 들어왔을때 받아올 에러 메시지
	 * @param model `Q&A 게시글 상세 내역` 또는 `에러 메시지`를 전달하기 위한 model
	 * @return Q&A 게시글 상세 보기 페이지
	 */
	@GetMapping("/{id}")
	public String questionDetail(@PathVariable long id, @ModelAttribute("errorMessage") String errorMessage,
		Model model, HttpSession session) throws QuestionNotExistException, NoAuthSessionException {
		AuthSessionValidator.validateUserIsSignedIn(session);

		if (errorMessage.isBlank()) {
			List<CommentResponseDTO> comment =
				commentService.findByPostId(id)
					.stream()
					.map(CommentResponseDTO::from)
					.collect(Collectors.toUnmodifiableList());

			model.addAttribute("questionDetailDTO",
				QuestionDetailResponseDTO.from(questionService.findById(id), comment));
		}

		return "qna/show";
	}

	/**
	 * 게시글 수정 페이지로 이동. (접근 권한: 게시글 작성자만)
	 * @param id 게시글 id
	 * @param model 게시글 내용을 보내주기 위한 model
	 * @param session 세션
	 * @return 게시글 수정 페이지
	 * @throws QuestionNotExistException 수정할 게시글이 없는 경우(삭제된 경우)
	 * @throws NoAccessPermissionException 접근 권한이 없는 경우
	 */
	@GetMapping("/{id}/modify-form")
	public String modifyForm(@PathVariable long id, Model model, HttpSession session) throws
		QuestionNotExistException, NoAccessPermissionException {

		QuestionEntity question = questionService.findById(id);
		AuthSessionValidator.validatePageOnlyWriterCanAccess(session, question.getWriter_id());
		model.addAttribute("questionDetailDTO", QuestionDetailResponseDTO.from(question, null));

		return "qna/modify-form";
	}

	/**
	 * Q&A 게시글 수정 기능. (접근 권한: 게시글 작성자만)
	 * @param id 게시글 id
	 * @param dto 수정할 게시글 정보
	 * @param session 세션
	 * @return 수정 성공시 수정된 게시물 보기 페이지로 이동
	 * @throws QuestionNotExistException 게시글이 없는 경우
	 * @throws NoAccessPermissionException 접근 권한이 없는 경우
	 */
	@PutMapping("/{id}")
	public String questionModify(@PathVariable long id, QuestionWriteRequestDTO dto, HttpSession session) throws
		QuestionNotExistException, NoAccessPermissionException {

		QuestionEntity question = questionService.findById(id);
		AuthSessionValidator.validatePageOnlyWriterCanAccess(session, question.getWriter_id());
		questionService.update(dto.toEntity(id, question.getWriter_id()));

		return "redirect:/questions/" + id;
	}

	/**
	 * Q&A 게시글 삭제 기능. (접근 권한: 게시글 작성자만)
	 * @param id 삭제할 게시글의 id
	 * @param session 세션
	 * @return 게시글 삭제 성공시 Q&A 게시글 목록 페이지로 이동
	 * @throws QuestionNotExistException 게시글이 없는 경우
	 * @throws NoAccessPermissionException 접근 권한이 없는 경우
	 */
	@DeleteMapping("/{id}")
	public String questionDelete(@PathVariable long id, HttpSession session) throws
		QuestionNotExistException, NoAccessPermissionException {

		QuestionEntity question = questionService.findById(id);
		AuthSessionValidator.validatePageOnlyWriterCanAccess(session, question.getWriter_id());
		questionService.delete(id);

		return "redirect:/questions";
	}

}
