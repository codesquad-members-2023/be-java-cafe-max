package kr.codesqaud.cafe.article;

import java.util.List;
import javax.servlet.http.HttpSession;
import kr.codesqaud.cafe.article.dto.ArticleRequestDto;
import kr.codesqaud.cafe.article.dto.ArticleResponseDto;
import kr.codesqaud.cafe.reply.Reply;
import kr.codesqaud.cafe.reply.ReplyService;
import kr.codesqaud.cafe.web.SessionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping("/articles/new")
    public String articlePage() {
        return "qna/form";
    }

    @PostMapping("/articles")
    public String write(final ArticleRequestDto articleRequestDto, HttpSession session) {
        String writer = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);
        Article article = articleRequestDto.toEntity(writer);
        long id = articleService.save(article);
        return "redirect:/articles/" + id;
    }

    @GetMapping("/articles/{articleId}")
    public String viewArticle(@PathVariable final long articleId, final Model model) {
        Article findArticle = articleService.findOne(articleId);
        ArticleResponseDto articleResponseDto = ArticleResponseDto.from(findArticle);
        model.addAttribute("article", articleResponseDto);

        Long replyCount = replyService.getReplyCountOf(articleId);
        model.addAttribute("replyCount", replyCount);

        List<Reply> findReplies = replyService.findReplies(articleId);
        model.addAttribute("replies", findReplies);
        return "qna/show";
    }

    @GetMapping("/articles/{articleId}/edit")
    public String showEditPage(@PathVariable final long articleId, final Model model) {
        Article findArticle = articleService.findOne(articleId);
        ArticleResponseDto articleResponseDto = ArticleResponseDto.from(findArticle);

        logger.info("게시글 수정 페이지 조회 요청 / " + "제목: " + findArticle.getTitle());

        model.addAttribute("article", articleResponseDto);
        return "qna/edit_form";
    }

    @PutMapping("/articles/{articleId}")
    public String editArticle
            (@PathVariable final long articleId, HttpSession session, final ArticleRequestDto articleRequestDto) {
        String requesterId = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);

        logger.info("requesterId: " + requesterId + " / 게시글 수정 요청");

        Article article = articleRequestDto.toEntity(requesterId);
        articleService.edit(articleId, article);
        return "redirect:/articles/{articleId}";
    }

    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable final long articleId, HttpSession session) {
        String requesterId = (String) session.getAttribute(SessionConstant.LOGIN_USER_ID);

        logger.info("requesterId: {}, articleId: {} / 게시글 삭제 요청", requesterId, articleId);

        articleService.delete(articleId, requesterId);
        return "redirect:/";
    }
}
