package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import kr.codesqaud.cafe.dto.post.PostResponseDto;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.service.post.PostService;

@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/write")
    public String write(@ModelAttribute @Validated PostWriteRequest postWriteRequest, @ModelAttribute PostResponseDto postResponseDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/write";
        }
        postService.save(postWriteRequest,postResponseDto.getPostId());
        return "redirect:/post/posts";
    }



}
