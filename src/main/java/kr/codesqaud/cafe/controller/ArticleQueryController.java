package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.dto.ArticleResponse;
import kr.codesqaud.cafe.dto.CommentResponse;
import kr.codesqaud.cafe.dto.Paging;
import kr.codesqaud.cafe.dto.UserRequest;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleQueryController {
	private final ArticleService articleService;

	public ArticleQueryController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping
	public String getArticleList() {
		return "/1";
	}

	@GetMapping("/{page}")
	public String getArticleListPage(@PathVariable int page, Model model) {
		List<ArticleResponse> articleResponses = articleService.findArticleResponsesPage(page);
		Paging paging = articleService.paging(page);
		List<Integer> pagingPrevNum = articleService.makePagingPrevNumber(paging.getStart(), page);
		List<Integer> pagingNextNum = articleService.makePagingNextNumber(page, paging.getEnd());
		model.addAttribute("articles", articleResponses);
		model.addAttribute("paging", paging);
		model.addAttribute("pagingPrevNum", pagingPrevNum);
		model.addAttribute("pagingNextNum", pagingNextNum);
		return "index";
	}

	@GetMapping("/articles/form")
	public String getArticleForm(HttpSession session, Model model) {
		String writer = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		model.addAttribute("writer", writer);
		return "article/form";
	}

	@GetMapping("/articles/{articleIndex}/{writer}")
	public String showArticle(@PathVariable Long articleIndex, @PathVariable String writer, HttpSession session,
		Model model) {
		articleService.increaseHits(articleIndex);
		String author = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		String equal = articleService.checkIsWriter(author, writer);
		ArticleResponse articleResponse = articleService.findByIndex(articleIndex);
		List<CommentResponse> commentResponses = articleService.showComments(articleIndex, 0L);

		model.addAttribute("author", author);
		model.addAttribute("article", articleResponse);
		model.addAttribute("comments", commentResponses);
		model.addAttribute("equal", equal);
		return "article/detail";
	}

	@GetMapping("/articles/{articleIndex}")
	public String getUpdateForm(@PathVariable Long articleIndex, Model model, HttpSession session) {
		String nickname = ((UserRequest)session.getAttribute("sessionUser")).getNickname();
		ArticleResponse articleResponse = articleService.findByArticleIndexForUpdate(articleIndex, nickname);
		model.addAttribute("article", articleResponse);
		return "article/updateDetail";
	}
}
