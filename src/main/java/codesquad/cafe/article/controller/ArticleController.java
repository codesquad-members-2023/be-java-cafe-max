package codesquad.cafe.article.controller;

import codesquad.cafe.article.dto.ArticleRequestDto;
import codesquad.cafe.article.dto.ArticleResponseDto;
import codesquad.cafe.article.dto.ArticleUpdateRequestDto;
import codesquad.cafe.article.service.ArticleService;
import codesquad.cafe.global.util.Criteria;
import codesquad.cafe.global.util.Page;
import codesquad.cafe.reply.dto.ReplyResponseDto;
import codesquad.cafe.reply.service.ReplyService;
import codesquad.cafe.user.domain.User;
import codesquad.cafe.global.constant.SessionAttributes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ArticleController {
    private final Log log = LogFactory.getLog(ArticleController.class);

    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(final ArticleService articleService, final ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping
    public String showHome(@RequestParam(defaultValue = "1") int page, Model model) {
        Criteria criteria = new Criteria(page);
        List<ArticleResponseDto> articles = articleService.getPagingArticles(criteria);
        int total = articleService.getTotal();

        model.addAttribute("articles", articles);
        model.addAttribute("page", new Page(criteria, total));
        return "index";
    }

    @PostMapping("/questions")
    public String writePost(@ModelAttribute @Valid ArticleRequestDto articleRequestDto,
                            HttpServletRequest request) {
        User user = findUserByRequest(request);
        articleService.createPost(articleRequestDto, user);
        return "redirect:/";
    }

    @GetMapping("/articles/{postId}")
    public String showDetailPost(@PathVariable Long postId, Model model) {
        ArticleResponseDto post = articleService.findPost(postId);
        List<ReplyResponseDto> replies = replyService.findReplies(postId);
        model.addAttribute("post", post);
        model.addAttribute("replies", replies);
        return "qna/show";
    }

    @GetMapping("/articles/{postId}/form")
    public String showUpdatePostForm(@PathVariable Long postId, Model model,
                                     HttpServletRequest request) {
        User user = findUserByRequest(request);
        ArticleResponseDto post = articleService.findPostByIdAndUser(postId, user);
        model.addAttribute("post", post);
        return "qna/updateForm";
    }

    @PutMapping("/articles/{postId}")
    public String updatePost(@PathVariable Long postId,
                             @Valid ArticleUpdateRequestDto articleUpdateRequestDto,
                             HttpServletRequest request) {
        User user = findUserByRequest(request);
        articleService.updatePost(articleUpdateRequestDto, postId, user);
        return "redirect:/articles/" + postId;
    }

    @DeleteMapping("/articles/{postId}")
    public String deletePost(@PathVariable Long postId, HttpServletRequest request) {
        User user = findUserByRequest(request);
        articleService.deletePost(postId, user);
        return "redirect:/";
    }

    private User findUserByRequest(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(SessionAttributes.LOGIN_USER.getValue());
    }
}
