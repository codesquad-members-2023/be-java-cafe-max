package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import kr.codesqaud.cafe.post.form.PostForm;
import kr.codesqaud.cafe.post.form.SimplePostForm;

@Controller
public class PostController {

	private final PostsRepository postsRepository;
	private final PostService postService;

	public PostController(PostsRepository postsRepository, PostService postService) {
		this.postsRepository = postsRepository;
		this.postService = postService;
	}

	@GetMapping("/post")
	public String showPostPage(Model model) {
		model.addAttribute(new PostForm());
		return "/post/form";
	}

	@PostMapping("/post")
	public String addPost(@Valid PostForm postForm, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "/post/form";
		}
		Post post = postService.createNewPost(postForm);
		model.addAttribute(post);
		return "/post/postDetail";
	}

	@GetMapping("/post/{postId}")
	public String showPostPage(Model model, @PathVariable Long postId) {
		Optional<Post> optionalPost = postsRepository.findById(postId);
		if (optionalPost.isEmpty()) {
			return "redirect:/";
		}
		Post post = optionalPost.get();
		model.addAttribute(post);
		return "/post/postDetail";
	}
}
