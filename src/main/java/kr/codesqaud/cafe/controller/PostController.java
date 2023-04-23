package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import javax.validation.Valid;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostEditRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.exception.common.CommonExceptionType;
import kr.codesqaud.cafe.service.PostService;
import kr.codesqaud.cafe.session.LoginMemberSession;

@Controller
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
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

    @GetMapping("/{postId}/edit-form")
    public String editForm(@PathVariable Long postId, Model model) {
        PostEditRequest postEditRequest = new PostEditRequest(0L, "", null);
        model.addAttribute("postEditRequest", postService.findById(postId));
        return "post/edit";
    }

    @PutMapping("/{postId}")
    public String editPost(@Valid PostEditRequest postEditRequest, BindingResult bindingResult, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        PostResponse postResponse = postService.findById(postEditRequest.getPostId());
        String postWriterEmail = postResponse.getWriter().getWriterEmail();
        if (loginMemberSession.isNotEqualMember(postWriterEmail)) {
            throw new CommonException(CommonExceptionType.ACCESS_DENIED);
        }

        if (bindingResult.hasErrors()) {
            return "post/edit";
        }

        postService.editPost(postEditRequest);
        return "redirect:/posts/{postId}";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, Model model) {
        model.addAttribute("postResponse", postService.findById(postId));
        return "post/post";
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        PostWriteRequest postWriteRequest = new PostWriteRequest("", "", null);
        model.addAttribute("postWriteRequest", postWriteRequest);
        return "post/write";
    }

    @GetMapping("/writer/{writerEmail}")
    public String findPostByWriterEmail(@PathVariable String writerEmail, Model model) {
        List<Post> posts = postService.findPostByWriterEmail(writerEmail);
        model.addAttribute("postResponses", posts);
        return "post/all";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable final Long postId, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        PostResponse postResponse = postService.findById(postId);
        String postWriterEmail = postResponse.getWriter().getWriterEmail();
        if (loginMemberSession.isNotEqualMember(postWriterEmail)) {
            throw new CommonException(CommonExceptionType.ACCESS_DENIED);
        }
        postService.deletePostId(postId);
        return "redirect:/";
    }
}
