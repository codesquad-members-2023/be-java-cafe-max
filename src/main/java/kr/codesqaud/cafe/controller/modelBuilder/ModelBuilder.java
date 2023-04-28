package kr.codesqaud.cafe.controller.modelBuilder;

import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ModelBuilder {
    private final ArticleService articleService;
    private final ReplyService replyService;

    public ModelBuilder(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    public void showFailed(Long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        model.addAttribute("articleId", articleId);
        model.addAttribute("reply", replyService.getReplies(articleId));
        model.addAttribute("replyCount", replyService.getReplyCount(articleId));
    }
}
