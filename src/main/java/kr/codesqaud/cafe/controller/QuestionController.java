package kr.codesqaud.cafe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
