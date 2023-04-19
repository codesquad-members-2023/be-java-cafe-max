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
import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.ReplyService;

@Controller
public class ArticleController {

	private final ArticleService articleService;

	private final ReplyService replyService;

	public ArticleController(ArticleService articleService, ReplyService replyService) {
		this.articleService = articleService;
		this.replyService = replyService;
	}

	@GetMapping("articles")
	public String articleForm() {
		return "article/form";
	}

	@PostMapping("/articles")
	public String post(@ModelAttribute @Valid ArticlePostRequest articlePostRequest, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articlePostRequest.setId(session.getId());
		articlePostRequest.setNickName(session.getNickName());
		articleService.post(articlePostRequest);
		return "redirect:/";
	}

	//todo join써서 db에 2번접근하지 말고 한번접근해 데이터 가져와보기
	@GetMapping("/articles/{idx}")
	public String detail(@PathVariable Long idx, Model model) {
		model.addAttribute("article", articleService.findArticleByIdx(idx));
		model.addAttribute("replies", replyService.getRepliesByIdx(idx));
		return "article/show";
	}

	@GetMapping("/articles/update/{idx}")
	public String updateForm(@PathVariable Long idx, Model model, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		model.addAttribute("article", articleService.validSessionIdAndArticleId(idx, session.getId()));
		model.addAttribute("idx", idx);
		return "article/updateForm";
	}

	@PutMapping("/articles/update/{idx}")
	public String update(@ModelAttribute @Valid ArticleUpdateRequest articleUpdateRequest,
		@PathVariable Long idx) {
		articleUpdateRequest.setIdx(idx);
		articleService.updateArticle(articleUpdateRequest);
		return "redirect:/";
	}

	@DeleteMapping("/articles/{idx}")
	public String delete(@PathVariable Long idx, HttpSession httpSession) {
		Session session = getLoginUser(httpSession);
		articleService.deleteArticleByIdx(idx, session.getId());
		return "redirect:/";
	}
}
