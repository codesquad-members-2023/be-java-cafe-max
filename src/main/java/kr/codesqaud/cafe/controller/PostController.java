package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.util.SignInSessionUtil.SIGN_IN_SESSION_NAME;

import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.comment.CommentPagination;
import kr.codesqaud.cafe.dto.post.PostPagination;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.CommentService;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/posts")
@Controller
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String showPosts(@RequestParam(defaultValue = "1") Integer page, Model model) {
        PostPagination postPagination = postService.getPagination(page);
        model.addAttribute("pagination", postPagination);
        model.addAttribute("postResponses", postService.findAll(postPagination.getOffset()));
        return "post/posts";
    }

    @GetMapping("/write/form")
    public String showWriteForm(PostWriteRequest postWriteRequest) {
        return "post/postWrite";
    }

    @PostMapping("/write")
    public String write(@Valid PostWriteRequest postWriteRequest, BindingResult bindingResult,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postWrite";
        }

        postWriteRequest.setWriterId(accountSession.getId());
        postService.write(postWriteRequest);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        model.addAttribute("postResponse", postService.findById(id));
        model.addAttribute("commentsResponse", commentService.findAllByPostId(id, 1));
        return "post/post";
    }

    @GetMapping("/{id}/form")
    public String showModifyForm(@PathVariable Long id, Model model,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        model.addAttribute("postModifyRequest", postService.findPostForModifying(id, accountSession.getId()));
        return "post/postModify";
    }

    @PutMapping("/{id}")
    public String modify(@Valid PostModifyRequest postModifyRequest, BindingResult bindingResult,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postModify";
        }

        postService.modify(postModifyRequest, accountSession.getId());
        return "redirect:/posts/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        postService.delete(id, accountSession.getId());
        return "redirect:/";
    }
}
