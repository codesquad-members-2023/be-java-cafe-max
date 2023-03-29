package kr.codesqaud.cafe.post;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.codesqaud.cafe.post.form.PostForm;

@Controller
public class PostController {

	private final PostsRepository postsRepository;
	private final PostService postService;

	public PostController(PostsRepository postsRepository, PostService postService) {
		this.postsRepository = postsRepository;
		this.postService = postService;
	}

	@GetMapping("/post")
	public String showPostPage(Model model, @RequestParam @Nullable boolean errors) {
		model.addAttribute(new PostForm());
		model.addAttribute("errors", errors);
		return "/post/form";
	}

	@PostMapping("/post")
	public String addPost(@Valid PostForm postForm, Errors errors, RedirectAttributes redirectAttributes, Model model) {
		if (errors.hasErrors()) {
			redirectAttributes.addAttribute("errors", true);
			return "redirect:/post";
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
