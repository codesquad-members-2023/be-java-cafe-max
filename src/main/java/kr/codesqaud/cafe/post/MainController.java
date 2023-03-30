package kr.codesqaud.cafe.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.post.Post;
import kr.codesqaud.cafe.post.PostService;
import kr.codesqaud.cafe.post.PostsRepository;
import kr.codesqaud.cafe.post.form.SimplePostForm;

@Controller
public class MainController {

	private final PostsRepository postsRepository;
	private final PostService postService;

	public MainController(PostsRepository postsRepository, PostService postService) {
		this.postsRepository = postsRepository;
		this.postService = postService;
	}

	@GetMapping("/")
	public String showMainPage(Model model) {
		List<Post> posts = postsRepository.getAllPosts();
		List<SimplePostForm> simpleForms = postService.mappingSimpleForm(posts);
		model.addAttribute("simpleForms", simpleForms);
		return "index";
	}

}
