package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.post.form.SimplePostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private static final String SIMPLE_FORMS = "simpleForms";
    private final PostRepository postRepository;
    private final PostService postService;

    public MainController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping
    public String showMainPage(Model model) {
        List<Post> posts = postRepository.getAllPosts();
        List<SimplePostForm> simpleForms = postService.mappingSimpleForm(posts);
        model.addAttribute(SIMPLE_FORMS, simpleForms);
        return "index";
    }

}
