package kr.codesqaud.cafe.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/posts")
@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/form")
    public String writeForm(PostWriteRequest postWriteRequest) {
        return "post/postWrite";
    }

    @PostMapping
    public String write(@Valid PostWriteRequest postWriteRequest, BindingResult bindingResult,
        @RequestAttribute AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postWrite";
        }

        postWriteRequest.setWriterId(accountSession.getId());
        postService.write(postWriteRequest);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("postResponse", postService.findById(id));
        return "post/post";
    }

    @GetMapping("/{id}/form")
    public String modifyForm(@PathVariable Long id, Model model, @RequestAttribute AccountSession accountSession) {
        postService.validateUnauthorized(id, accountSession.getId());
        model.addAttribute("postModifyRequest", postService.findById(id));
        return "post/postModify";
    }

    @PutMapping("/{id}")
    public String modify(@Valid PostModifyRequest postModifyRequest, BindingResult bindingResult,
        @RequestAttribute AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "post/postModify";
        }

        postService.modify(postModifyRequest, accountSession.getId());
        return "redirect:/posts/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, @RequestAttribute AccountSession accountSession) {
        postService.delete(id, accountSession.getId());
        return "redirect:/";
    }
}
