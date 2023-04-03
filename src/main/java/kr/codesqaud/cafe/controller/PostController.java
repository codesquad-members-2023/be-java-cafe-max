package kr.codesqaud.cafe.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/posts")
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

    @PostMapping
    public String write(@Valid PostWriteRequest postWriteRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "post/write";
        }

        postService.save(postWriteRequest);
        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String post(@PathVariable Long id, Model model) {
        model.addAttribute("postResponse", postService.findById(id));
        return "post/post";
    }

    @GetMapping("/write")
    public String writeForm(PostWriteRequest postWriteRequest) {
        return "post/write";
    }
}
