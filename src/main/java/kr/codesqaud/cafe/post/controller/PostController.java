package kr.codesqaud.cafe.post.controller;

import kr.codesqaud.cafe.post.domain.Post;
import kr.codesqaud.cafe.post.service.AuthService;
import kr.codesqaud.cafe.post.service.PostService;
import kr.codesqaud.cafe.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

@RequestMapping("/post")
@Controller
public class PostController {
    private final PostService postService;
    private final AuthService authService;

    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;

    }

    @GetMapping("/form")
    public String form(HttpSession httpSession) {
        if(!authService.checkLogin(httpSession)){
            return "redirect:/user/login";
        }
        return "post/form";
    }

    @GetMapping("/{index}")
    public String show(@PathVariable long index, Model model,HttpSession httpSession) throws AccessDeniedException {
        if(!authService.checkLogin(httpSession)){
            return "redirect:/user/login";
        }
        model.addAttribute("post", postService.findById(index));
        return "post/show";
    }

    @PostMapping("/form")
    public String write(PostCreateRequest postCreateRequest,HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("sessionUser");
        Post post = new Post.Builder()
                .title(postCreateRequest.getTitle())
                .contents(postCreateRequest.getContents())
                .writer(user.getName())
                .build();
        postService.create(post);
        return "redirect:/";
    }

    @GetMapping("/{index}/edit")
    public String edit(@PathVariable long index, HttpSession httpSession,Model model){
        if(!authService.checkLogin(httpSession)){
            return "redirect:/user/login";
        }
        if(!authService.checkAuth(httpSession, postService.findById(index))){
            return "redirect:/post/error";
        }
        model.addAttribute("index",index);
        return "post/edit";
    }

    @PutMapping("/{index}/edit")
    public String update(@PathVariable long index,PostCreateRequest postCreateRequest){
        postService.edit(postCreateRequest,index);
        return "redirect:/post/"+index;
    }
}

