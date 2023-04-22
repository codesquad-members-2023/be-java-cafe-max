package kr.codesqaud.cafe.domain.article.controller;

import static kr.codesqaud.cafe.global.common.constant.SessionAttributeNames.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.codesqaud.cafe.domain.article.dto.request.ArticleSaveRequestDto;
import kr.codesqaud.cafe.domain.article.dto.response.ArticleDetailResponseDto;
import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.domain.reply.dto.response.ReplyDetailResponseDto;
import kr.codesqaud.cafe.domain.reply.entity.Reply;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;
	private final ReplyRepository replyRepository;

	public ArticleController(ArticleRepository articleRepository, ReplyRepository replyRepository) {
		this.articleRepository = articleRepository;
		this.replyRepository = replyRepository;
	}

	@GetMapping("")
	public String main(Model model) {
		List<Article> articles = articleRepository.findAll();
		List<ArticleDetailResponseDto> articleDetailResponseDtos = new ArrayList<>();
		articles.forEach(article -> articleDetailResponseDtos.add(new ArticleDetailResponseDto(article)));
		model.addAttribute("articles", articleDetailResponseDtos);
		return "index";
	}

	@GetMapping("/articles")
	public String saveArticle(ArticleSaveRequestDto articleSaveRequestDto, HttpSession httpSession) {
		String loginUserName = httpSession.getAttribute(LOGIN_USER_NAME.type()).toString();
		articleRepository.save(articleSaveRequestDto.toEntity(loginUserName));
		return "redirect:/";
	}

	@GetMapping("/articles/{articleId}")
	public String viewArticle(@PathVariable("articleId") Long articleId, Model model) {
		Article article = articleRepository.findById(articleId).orElseThrow(NoSuchElementException::new);
		List<Reply> replies = replyRepository.findByArticleId(articleId);
		List<ReplyDetailResponseDto> replyDetailResponseDtos = new ArrayList<>();
		replies.forEach(reply -> replyDetailResponseDtos.add(new ReplyDetailResponseDto(reply)));
		model.addAttribute("article", new ArticleDetailResponseDto(article));
		model.addAttribute("replies", replyDetailResponseDtos);
		return "/post/show";
	}
}
