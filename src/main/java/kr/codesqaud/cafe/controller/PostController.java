package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.validation.Valid;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;

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
        return "redirect:/write";
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
    public String findPostBywriterEmail(@PathVariable String writerEmail, Model model) {
        List<Post> posts = postService.findPostByWriterEmail(writerEmail);
        model.addAttribute("postResponses", posts);
        return "post/all";
    }
}
