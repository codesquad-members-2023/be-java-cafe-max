package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.common.consts.SessionConst.SESSION_USER;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.ArticleDetails;
import kr.codesqaud.cafe.controller.dto.req.ArticleEditRequest;
import kr.codesqaud.cafe.controller.dto.req.PostingRequest;
import kr.codesqaud.cafe.service.ArticleService;

@RequestMapping("/articles")
@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping
	public String post(@ModelAttribute final PostingRequest request,
					   @SessionAttribute(SESSION_USER) final String userId) {
		articleService.post(request, userId);
		return "redirect:/";
	}

	@GetMapping("/{articleId}")
	public String showArticleDetails(@PathVariable final Long articleId, final Model model) {
		ArticleDetails articleDetails = articleService.getArticleDetails(articleId);
		model.addAttribute("article", articleDetails.getArticleResponse());
		model.addAttribute("articleCommentCount", articleDetails.getArticleCommentResponse().size());
		model.addAttribute("articleComments", articleDetails.getArticleCommentResponse());
		return "qna/show";
	}

	@GetMapping("/{articleId}/form")
	public String showArticleEditPage(@PathVariable final Long articleId,
									  @SessionAttribute(SESSION_USER) final String userId,
									  final Model model) {
		articleService.validateHasAuthorization(articleId, userId);
		model.addAttribute("articleId", articleId);
		return "qna/edit_form";
	}

	@PutMapping("/{articleId}")
	public String editArticle(@PathVariable final Long articleId, @ModelAttribute final ArticleEditRequest request) {
		articleService.editArticle(articleId, request);
		return "redirect:/articles/" + articleId;
	}

	@DeleteMapping("/{articleId}")
	public String deleteArticle(@PathVariable final Long articleId,
								@SessionAttribute(SESSION_USER) final String userId) {
		articleService.validateHasAuthorization(articleId, userId);
		articleService.deleteArticle(articleId);
		return "redirect:/";
	}
}
