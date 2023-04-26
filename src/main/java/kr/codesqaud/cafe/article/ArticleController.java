package kr.codesqaud.cafe.article;

import static kr.codesqaud.cafe.global.config.Session.*;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;
import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.reply.ReplyService;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;

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
	public String post(@ModelAttribute @Valid ArticlePostRequest articlePostRequest,
		@SessionAttribute(LOGIN_USER) Session session) {
		articlePostRequest.setUserId(session.getId());
		articlePostRequest.setNickName(session.getNickName());
		articleService.post(articlePostRequest);
		return "redirect:/";
	}

	//todo join써서 db에 2번접근하지 말고 한번접근해 데이터 가져와보기
	@GetMapping("/articles/{articleIdx}")
	public String detail(@PathVariable Long articleIdx, Model model) {
		Long countOfReplies = replyService.getCountOfReplies(articleIdx);
		LoadMoreReplyDto loadMoreReplyDto = new LoadMoreReplyDto(articleIdx, countOfReplies, 0l);

		model.addAttribute("article", articleService.findArticleByIdx(articleIdx));
		model.addAttribute("replies", replyService.getRepliesByIdx(loadMoreReplyDto));
		model.addAttribute("loadReplies", loadMoreReplyDto);

		return "article/show";
	}

	@GetMapping("/articles/update/{articleIdx}")
	public String updateForm(@PathVariable Long articleIdx, Model model,
		@SessionAttribute(LOGIN_USER) Session session) {
		model.addAttribute("article", articleService.validSessionIdAndArticleId(articleIdx, session.getId()));
		model.addAttribute("idx", articleIdx);
		return "article/updateForm";
	}

	@PutMapping("/articles/update/{articleIdx}")
	public String update(@ModelAttribute @Valid ArticleUpdateRequest articleUpdateRequest,
		@PathVariable Long articleIdx) {
		articleUpdateRequest.setArticleIdx(articleIdx);
		articleService.updateArticle(articleUpdateRequest);
		return "redirect:/";
	}

	@DeleteMapping("/articles/{articleIdx}")
	public String delete(@PathVariable Long articleIdx, @SessionAttribute(LOGIN_USER) Session session) {
		articleService.deleteArticleByIdx(articleIdx, session.getId());
		return "redirect:/";
	}
}
