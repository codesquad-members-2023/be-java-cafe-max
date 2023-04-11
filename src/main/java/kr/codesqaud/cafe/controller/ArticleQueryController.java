package kr.codesqaud.cafe.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.domain.Article;
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

	@GetMapping("/questions/form")
	public String getArticleForm(HttpSession session, Model model) {
		Object value = session.getAttribute("sessionUser");
		if (value == null) {
			return "redirect:/user/login";
		}
		User user = (User)value;
		model.addAttribute("writer", user.getNickname());
		return "qna/form";
	}

	@GetMapping("/articles/{index}/{writer}")
	public String showArticle(@PathVariable long index, @PathVariable String writer, HttpSession session, Model model) {
		String equal = null;
		Object value = session.getAttribute("sessionUser");
		if (value == null) {
			return "redirect:/user/login";
		}
		User user = (User)value;
		if (user.getNickname().equals(writer)) {
			equal = "true";
		}
		articleService.increaseHits(index);
		Article article = articleService.findByIndex(index);
		model.addAttribute("article", article);
		model.addAttribute("equal", equal);
		return "qna/detail";
	}
}
