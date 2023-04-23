package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.ArticleService;

@Controller
public class ArticleQueryController {
	private final ArticleService articleService;

	public ArticleQueryController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@GetMapping("/")
	public String getArticleList(Model model) {
		List<Article> articles = articleService.findArticles();
		model.addAttribute("articles", articles);
		return "index";
	}

	@GetMapping("/article/form")
	public String getArticleForm(HttpSession session, Model model) {
		User user = (User)session.getAttribute("sessionUser");
		model.addAttribute("writer", user.getNickname());
		return "article/form";
	}

	@GetMapping("/article/{index}/{writer}")
	public String showArticle(@PathVariable long index, @PathVariable String writer, HttpSession session, Model model) {
		String equal = null;
		User user = (User)session.getAttribute("sessionUser");
		if (user.getNickname().equals(writer)) {
			equal = "true";
		}
		articleService.increaseHits(index);
		Article article = articleService.findByIndex(index);
		List<Comment> comments = articleService.findCommentsByPostIndex(index);
		model.addAttribute("user", user);
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		model.addAttribute("equal", equal);
		return "article/detail";
	}

	@GetMapping("/article/update/{index}")
	public String getUpdateForm(@PathVariable Long index, Model model, HttpSession session) {
		User user = (User)session.getAttribute("sessionUser");
		Article article = articleService.findByIndex(index);
		article.validateWriter(user.getNickname(), "다른 사람의 글은 수정할 수 없습니다.");
		model.addAttribute("article", article);
		return "article/updateDetail";
	}
}
