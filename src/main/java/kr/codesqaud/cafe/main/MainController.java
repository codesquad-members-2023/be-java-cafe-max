package kr.codesqaud.cafe.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.codesqaud.cafe.post.Post;
import kr.codesqaud.cafe.post.PostsRepository;

@Controller
public class MainController {

	private final PostsRepository postsRepository;

	public MainController(PostsRepository postsRepository) {
		this.postsRepository = postsRepository;
	}

	@GetMapping("/")
	public String showMainPage(Model model) {
		List<Post> posts = postsRepository.getAllPosts();
		model.addAttribute("posts", posts);
		return "index";
	}

}
