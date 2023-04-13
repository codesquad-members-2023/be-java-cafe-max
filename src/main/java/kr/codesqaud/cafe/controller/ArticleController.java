package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleController {
	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/qna/form")
	public String createNewPosting(@ModelAttribute PostingRequest postingRequest, HttpSession session) {
		Object userId = session.getAttribute("sessionedUser");
		articleService.articleSave(postingRequest, (String)userId);
		return "redirect:/";
	}

	@GetMapping("/")
	public String postingListLookup(Model model) {
		model.addAttribute("articles", articleService.allListLookup());
		return "index";
	}

	@GetMapping("/articles/{id}")
	public String postDetails(Model model, @PathVariable Long id) {
		model.addAttribute("details", articleService.findById(id));
		return "qna/show";
	}

	@GetMapping("/questions/form")
	public String newPosting(HttpSession session) {
		Object user = session.getAttribute("sessionedUser");
		if (user == null) {
			return "redirect:/users/login";
		}
		return "qna/form";
	}

	@GetMapping("/articles/edit/{id}")
	public String editPost(Model model, @PathVariable Long id, HttpSession session) {
		Object userId = session.getAttribute("sessionedUser");
		ArticleDto articleDto = articleService.findById(id);
		if (!articleDto.getWriter().equals(userId)) {
			return "qna/access_error";
		}
		model.addAttribute("edits", articleDto);
		return "qna/edit_form";
	}
}
