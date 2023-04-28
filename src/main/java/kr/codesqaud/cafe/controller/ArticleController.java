package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.CommentDto;
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
	public String createNewPosting(@ModelAttribute PostingRequest postingRequest,
		@SessionAttribute("sessionedUser") String userId) {
		articleService.articleSave(postingRequest, userId);
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
		List<CommentDto> comments = commentService.getAllCommentsByArticleId(id);

		model.addAttribute("comments", comments);
		model.addAttribute("commentCount", comments.size());
		return "qna/show";
	}

	@GetMapping("/questions/form")
	public String newPosting() {
		return "qna/form";
	}

	@GetMapping("/articles/{id}/edit")
	public String editPost(Model model, @PathVariable Long id, @SessionAttribute("sessionedUser") String userId) {
		articleService.validateAuthorization(id, userId);
		model.addAttribute("edits", articleService.findById(id));
		return "qna/edit_form";
	}

	@DeleteMapping("/articles/{id}")
	public String deletePost(@PathVariable Long id, @SessionAttribute("sessionedUser") String userId) {
		articleService.validateAuthorization(id, userId);
		articleService.deleteRequest(id);
		return "redirect:/";
	}

	@PutMapping("/articles/{id}")
	public String updatePost(@ModelAttribute PostingRequest postingRequest, @PathVariable Long id,
		@SessionAttribute("sessionedUser") String writer) {
		articleService.updateRequest(postingRequest, id, writer);
		return "redirect:/articles/{id}";
	}
}
