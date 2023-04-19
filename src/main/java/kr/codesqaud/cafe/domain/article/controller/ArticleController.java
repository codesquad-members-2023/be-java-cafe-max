package kr.codesqaud.cafe.domain.article.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.domain.article.dto.request.ArticleSaveRequestDto;
import kr.codesqaud.cafe.domain.article.dto.response.ArticleDetailResponseDto;
import kr.codesqaud.cafe.domain.article.entity.Article;
import kr.codesqaud.cafe.domain.article.repository.ArticleRepository;
import kr.codesqaud.cafe.domain.reply.dto.response.ReplyDetailResponseDto;
import kr.codesqaud.cafe.domain.reply.entity.Reply;
import kr.codesqaud.cafe.domain.reply.repository.ReplyRepository;
import kr.codesqaud.cafe.global.common.constant.SessionAttributeNames;

@Controller
public class ArticleController {

	private final ArticleRepository articleRepository;
	private final ReplyRepository replyRepository;

	public ArticleController(ArticleRepository articleRepository, ReplyRepository replyRepository) {
		this.articleRepository = articleRepository;
		this.replyRepository = replyRepository;
	}

	@GetMapping("/")
	public String main(Model model) {
		List<Article> articles = articleRepository.findAll();
		List<ArticleDetailResponseDto> articleDetailResponseDtos = new ArrayList<>();
		articles.forEach(article -> articleDetailResponseDtos.add(new ArticleDetailResponseDto(article)));
		model.addAttribute("articles", articleDetailResponseDtos);
		return "index";
	}

	@PostMapping("/articles")
	public String saveArticle(ArticleSaveRequestDto articleSaveRequestDto, HttpSession httpSession) {
		articleRepository.save(
			articleSaveRequestDto.toEntity(
				(String)httpSession.getAttribute(SessionAttributeNames.LOGIN_USER_NAME.type())));
		return "redirect:/";
	}

	@GetMapping("/articles/{id}")
	public String viewArticle(@PathVariable("id") Long id, Model model) {
		Article article = articleRepository.findById(id).orElseThrow(NoSuchElementException::new);
		List<Reply> replies = replyRepository.findByArticleId(id);
		List<ReplyDetailResponseDto> replyDetailResponseDtos = new ArrayList<>();
		replies.forEach(reply -> replyDetailResponseDtos.add(new ReplyDetailResponseDto(reply)));
		model.addAttribute("article", new ArticleDetailResponseDto(article));
		model.addAttribute("replies", replyDetailResponseDtos);
		return "/post/show";
	}
}
