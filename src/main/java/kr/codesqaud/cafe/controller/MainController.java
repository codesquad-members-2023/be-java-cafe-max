package kr.codesqaud.cafe.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.controller.dto.ArticleWithCommentCount;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.paging.Page;
import kr.codesqaud.cafe.service.paging.PageNumber;
import kr.codesqaud.cafe.service.paging.Pageable;
import kr.codesqaud.cafe.service.paging.PaginationService;

@Controller
public class MainController {

	private final ArticleService articleService;
	private final PaginationService paginationService;

	public MainController(ArticleService articleService, PaginationService paginationService) {
		this.articleService = articleService;
		this.paginationService = paginationService;
	}

	@GetMapping("/")
	public String showMainPage(final Pageable pageable, final Model model) {
		Page<ArticleWithCommentCount> articles = articleService.getArticlesWithCommentCount(pageable);
		List<PageNumber> barNumbers = paginationService.getPaginationBarNumbers(pageable.getPageNumber(),
		                                                                        articles.getTotalPages());
		model.addAttribute("articles", articles.getContents());
		model.addAttribute("prevNumber", paginationService.getPrevPageNumber(pageable.getPageNumber()));
		model.addAttribute("paginationBarNumbers", barNumbers);
		model.addAttribute("nextNumber",
		                   paginationService.getNextPageNumber(pageable.getPageNumber(), articles.getTotalPages()));
		return "index";
	}
}
