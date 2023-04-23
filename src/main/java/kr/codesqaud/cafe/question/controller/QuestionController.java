package kr.codesqaud.cafe.question.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.common.web.PageHandler;
import kr.codesqaud.cafe.question.controller.request.QuestionWriteRequestDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionBoardResponseDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionDetailDTO;
import kr.codesqaud.cafe.question.controller.response.QuestionTitleResponseDTO;
import kr.codesqaud.cafe.question.exception.QuestionNotExistException;
import kr.codesqaud.cafe.question.service.QuestionService;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	private final QuestionService service;

	public QuestionController(QuestionService service) {
		this.service = service;
	}

	/**
	 * Q&A 게시글 작성 페이지로 이동
	 * @return Q&A 게시글 작성 페이지
	 */
	@GetMapping("/write-form")
	public String writeForm() {
		return "qna/form";
	}

	/**
	 * Q&A 게시글 쓰기 기능
	 * @param dto 게시글 쓰기용 dto
	 * @return Q&A 게시글 작성 페이지로 redirect
	 */
	@PostMapping
	public String questionAdd(QuestionWriteRequestDTO dto) {
		service.save(dto.toEntity());
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
		PageHandler pageHandler = new PageHandler(service.countBy(), page);

		List<QuestionTitleResponseDTO> questionTitles =
			service.findAll(pageHandler.getPostOffset(), pageHandler.getPageSize())
				.stream()
				.map(QuestionTitleResponseDTO::from)
				.collect(Collectors.toUnmodifiableList());

		model.addAttribute("questionBoardResponseDTO",
			new QuestionBoardResponseDTO(pageHandler, questionTitles));

		return "index";
	}

	/**
	 * Q&A 게시글 상세 보기 페이지로 이동
	 * @param id 조회하고자 하는 Q&A 게시글의 id
	 * @param errorMessage 없는 게시글 또는 잘못된 입력값이 들어왔을때 받아올 에러 메시지
	 * @param model `Q&A 게시글 상세 내역` 또는 `에러 메시지`를 전달하기 위한 model
	 * @return Q&A 게시글 상세 보기 페이지
	 */
	@GetMapping("/{id}")
	public String questionDetail(@PathVariable String id, @ModelAttribute("errorMessage") String errorMessage,
		Model model) throws QuestionNotExistException {
		if (errorMessage.isBlank()) {
			model.addAttribute("questionDetailDTO", QuestionDetailDTO.from(service.findById(Long.parseLong(id))));
		}

		return "qna/show";
	}

}
