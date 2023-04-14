package kr.codesqaud.cafe.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;
import kr.codesqaud.cafe.session.AccountSession;
import kr.codesqaud.cafe.session.SignIn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String posts(Model model) {
        model.addAttribute("postResponses", postService.findAll());
        return "post/posts";
    }

    @PostMapping("/posts")
    public String write(@Valid PostWriteRequest postWriteRequest, BindingResult bindingResult,
        @SignIn AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postWrite";
        }

        postService.write(postWriteRequest, accountSession.getId());
        return "redirect:/";
    }

    @GetMapping("/posts/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("postResponse", postService.findById(id));
        return "post/post";
    }

    @PutMapping("/posts/{id}")
    public String modify(@PathVariable Long id, @Valid PostModifyRequest postModifyRequest,
        BindingResult bindingResult, @SignIn AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postModify";
        }

        postService.validateUnauthorized(id, accountSession);
        postService.modify(postModifyRequest, id);
        return "redirect:/posts/{id}";
    }

    @DeleteMapping("/posts/{id}")
    public String delete(@PathVariable Long id, @SignIn AccountSession accountSession) {
        postService.validateUnauthorized(id, accountSession);
        postService.delete(id);
        return "redirect:/";
    }

    @GetMapping("/posts/write")
    public String writeForm(PostWriteRequest postWriteRequest) {
        return "post/postWrite";
    }

    @GetMapping("/posts/{id}/modify")
    public String modifyForm(@PathVariable Long id, Model model, @SignIn AccountSession accountSession) {
        postService.validateUnauthorized(id, accountSession);
        model.addAttribute("postModifyRequest", PostModifyRequest.from(postService.findById(id)));
        return "post/postModify";
    }
}
