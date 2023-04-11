package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.dto.PostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = {"/posts/new", "/posts/form"})
    public String viewPostForm(@ModelAttribute PostForm postForm, HttpSession httpSession) {
        Object sessionAttribute = httpSession.getAttribute("user");
        if (sessionAttribute == null) {
            return "redirect:/users/login";
        }
        return "/post/form";
    }

    @PostMapping("/posts")
    public String savePost(@Valid PostForm postForm, Errors errors, HttpSession httpSession) {
        if (errors.hasErrors()) {
            return "/post/form";
        }
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }
        Post post = postService.save(postForm, user);
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/posts/{postId}")
    public String viewPost(Model model, @PathVariable int postId, HttpSession httpSession) {
        Object sessionAttribute = httpSession.getAttribute("user");
        if (sessionAttribute == null) {
            return "redirect:/users/login";
        }
        Optional<Post> postOptional = postService.findById(postId);
        if (postOptional.isEmpty()) {
            throw new RuntimeException();
        }
        model.addAttribute(postOptional.get());
        return "/post/detail";
    }
}
