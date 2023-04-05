package kr.codesqaud.cafe.controller;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.codesqaud.cafe.dto.QuestionWriteDTO;
import kr.codesqaud.cafe.service.QuestionService;

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
	public String questionAdd(QuestionWriteDTO dto) {
		service.addQuestion(dto);
		return "redirect:questions/write-form";
	}

	/**
	 * Q&A 게시글 목록 페이지로 이동
	 * @param page 페이지 번호
	 * @param model `게시글`, `페이징 정보`를 담고 있는 dto 를 전달하기 위한 model
	 * @return Q&A 게시글 목록 페이지
	 */
	@GetMapping
	public String questionList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		Model model) {

		model.addAttribute("questionBoardDto", service.makeQuestionBoard(page));

		return "index";
	}

	/**
	 * Q&A 게시글 상세 보기 페이지로 이동
	 * @param questionIdx 조회하고자 하는 Q&A 게시글의 idx
	 * @param model `Q&A 게시글 상세 내역` 또는 `에러 메시지`를 전달하기 위한 model
	 * @return Q&A 게시글 상세 보기 페이지
	 */
	@GetMapping("/{questionIdx}")
	public String questionDetails(@PathVariable String questionIdx, Model model) {

		try {
			int idx = Integer.parseInt(questionIdx);
			model.addAttribute("questionDetails", service.findQuestion(idx));
		} catch (NumberFormatException e) {
			model.addAttribute("errorMessage", "잘못된 값이 입력되었습니다.");
		} catch (NoSuchElementException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "qna/show";
	}

}
