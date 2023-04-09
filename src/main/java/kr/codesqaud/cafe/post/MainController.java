package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.post.dto.SimplePostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String viewIndex(Model model) {
        List<SimplePostForm> simpleForms = postService.getAllPosts();
        model.addAttribute("simpleForms",simpleForms);
        return "index";
    }

}
