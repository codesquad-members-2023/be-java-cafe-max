package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.controller.dto.ArticleDto;
import kr.codesqaud.cafe.controller.dto.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.CommentService;

@Controller
public class ArticleController {
	private final ArticleService articleService;
	private final CommentService commentService;

	public ArticleController(ArticleService articleService, CommentService commentService) {
		this.articleService = articleService;
		this.commentService = commentService;
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
	public String postDetails(Model model, @PathVariable Long id, HttpSession session) {
		Object user = session.getAttribute("sessionedUser");
		if (user == null) {
			return "redirect:/users/login";
		}
		model.addAttribute("details", articleService.findById(id));
		model.addAttribute("contents", commentService.articleComment(id));
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

	@GetMapping("/articles/{id}/edit")
	public String editPost(Model model, @PathVariable Long id, HttpSession session) {
		Object userId = session.getAttribute("sessionedUser");
		ArticleDto articleDto = articleService.findById(id);
		if (!articleDto.getWriter().equals(userId)) {
			return "qna/access_error";
		}
		model.addAttribute("edits", articleDto);
		return "qna/edit_form";
	}

	@DeleteMapping("/articles/{id}/delete")
	public String deletePost(@PathVariable Long id, HttpSession session) {
		Object userId = session.getAttribute("sessionedUser");
		ArticleDto articleDto = articleService.findById(id);
		if (!articleDto.getWriter().equals(userId)) {
			return "qna/access_error";
		}
		articleService.deleteRequest(id);
		return "redirect:/";
	}

	@PutMapping("/articles/{id}/edit")
	public String updatePost(@ModelAttribute PostingRequest postingRequest, @PathVariable Long id,
		HttpSession session) {
		Object writer = session.getAttribute("sessionedUser");
		articleService.updateRequest(postingRequest, id, (String)writer);
		return "redirect:/";
	}
}
