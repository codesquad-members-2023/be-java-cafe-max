package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import javax.validation.Valid;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostEditRequest;

import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.CommentService;
import kr.codesqaud.cafe.service.PostService;
import kr.codesqaud.cafe.session.LoginMemberSession;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String posts(Model model) {
        model.addAttribute("postResponses", postService.findAll());
        return "post/all";
    }

    @PostMapping("/write")
    public String write(@Valid PostWriteRequest postWriteRequest, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/write";
        }
        postWriteRequest.setWriterEmail(loginMemberSession.getMemberEmail());
        postService.save(postWriteRequest);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit-form")
    public String editForm(@PathVariable Long id, Model model) {
        PostEditRequest postEditRequest = new PostEditRequest(0L, "", null);
        model.addAttribute("postEditRequest", postService.findById(id));
        return "post/edit";
    }

    @PutMapping("/{id}")
    public String editPost(@Valid PostEditRequest postEditRequest, BindingResult bindingResult, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        if (bindingResult.hasErrors()) {
            return "post/edit";
        }

        postService.editPost(postEditRequest, loginMemberSession);
        return "redirect:/posts/{id}";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("postResponse", postService.findById(id));
        model.addAttribute("commentListDto", commentService.findComments(id));
        return "post/post";
    }

    @GetMapping("/write")
    public String writeForm(@ModelAttribute PostWriteRequest postWriteRequest) {
        return "post/write";
    }

    @GetMapping("/writer/{writerEmail}")
    public String findPostByWriterEmail(@PathVariable String writerEmail, Model model) {
        List<Post> posts = postService.findPostByWriterEmail(writerEmail);
        model.addAttribute("postResponses", posts);
        return "post/all";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable final Long id, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        postService.deleteId(id,loginMemberSession);
        return "redirect:/";
    }
}
