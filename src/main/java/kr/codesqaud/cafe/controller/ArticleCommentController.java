package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.common.consts.SessionConst.SESSION_USER;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.controller.dto.req.ReplyRequest;
import kr.codesqaud.cafe.service.ArticleCommentService;

@Controller
public class ArticleCommentController {

	private final ArticleCommentService articleCommentService;

	public ArticleCommentController(ArticleCommentService articleCommentService) {
		this.articleCommentService = articleCommentService;
	}

	@PostMapping("/comments")
	public String reply(@ModelAttribute final ReplyRequest request,
						@SessionAttribute(SESSION_USER) final String userId) {
		articleCommentService.reply(request, userId);
		return "redirect:/articles/" + request.getArticleId();
	}
}
