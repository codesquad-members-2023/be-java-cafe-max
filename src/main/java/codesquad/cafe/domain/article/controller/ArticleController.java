package codesquad.cafe.domain.article.controller;

import codesquad.cafe.domain.article.dto.ArticleRequestDto;
import codesquad.cafe.domain.article.dto.ArticleResponseDto;
import codesquad.cafe.domain.article.dto.ArticleUpdateRequestDto;
import codesquad.cafe.domain.article.service.ArticleService;
import codesquad.cafe.domain.reply.dto.ReplyResponseDto;
import codesquad.cafe.domain.reply.service.ReplyService;
import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.global.constant.SessionAttributes;
import codesquad.cafe.global.interceptor.LoginCheckInterceptor;
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
    public String showHome(Model model) {
        List<ArticleResponseDto> posts = articleService.findPosts();
        model.addAttribute("posts", posts);
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
