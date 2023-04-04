package kr.codesqaud.cafe.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	public QuestionController(QuestionService service) {
		this.service = service;
	}

	@GetMapping("/write-form")
	public String writeForm() {
		return "qna/form";
	}

	@PostMapping("")
	public String questionAdd(QuestionWriteDTO dto) {
		service.addQuestion(dto);
		return "redirect:questions/write-form";
	}

	@GetMapping
	public String questionList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		Model model) {

		model.addAttribute("questionBoardDto", service.makeQuestionBoard(page));

		return "index";
	}

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
