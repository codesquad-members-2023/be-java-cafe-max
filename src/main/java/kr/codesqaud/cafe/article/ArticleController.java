package kr.codesqaud.cafe.article;

import static kr.codesqaud.cafe.global.config.Session.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.article.dto.ReplyRequest;
import kr.codesqaud.cafe.global.config.Session;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@PostMapping("/articles/submit")
	public String postArticle(@ModelAttribute @Valid ArticlePostRequest articlePostRequest, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articlePostRequest.setId(session.getId());
		articlePostRequest.setNickName(session.getNickName());
		articleService.post(articlePostRequest);
		return "redirect:/";
	}

	@GetMapping("/articles/{idx}")
	public String showDetailArticle(@PathVariable Long idx, Model model) {
		model.addAttribute("article", articleService.findArticleByIdx(idx));
		model.addAttribute("reply", articleService.getReplyListByIdx(idx));
		return "article/show";
	}

	@GetMapping("/articles/update-form/{idx}")
	public String showUpdateForm(@PathVariable Long idx, Model model, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		model.addAttribute("article", articleService.validSessionIdAndArticleId(idx, session.getId()));
		model.addAttribute("idx", idx);
		return "article/updateForm";
	}

	@PutMapping("/articles/submit/update-form/{idx}")
	public String updateArticle(@ModelAttribute @Valid ArticleUpdateRequest articleUpdateRequest,
		@PathVariable Long idx) {
		articleUpdateRequest.setIdx(idx);
		articleService.updateArticle(articleUpdateRequest);
		return "redirect:/";
	}

	@DeleteMapping("/articles/{idx}/delete")
	public String deleteArticle(@PathVariable Long idx, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articleService.deleteArticleByIdx(idx, session.getId());
		return "redirect:/";
	}

	@PostMapping("/articles/{idx}/rely")
	public String reply(@PathVariable Long idx, @ModelAttribute @Valid ReplyRequest replyRequest,
		HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		replyRequest.init(session.getId(), session.getNickName(), idx);
		articleService.addReply(replyRequest);
		return "redirect:/articles/" + idx;
	}

}
