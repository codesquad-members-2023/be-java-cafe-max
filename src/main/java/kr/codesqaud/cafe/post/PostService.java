package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.post.form.PostForm;
import kr.codesqaud.cafe.post.form.SimplePostForm;

@Service
public class PostService {

	private final PostsRepository postsRepository;

	public PostService(PostsRepository postsRepository) {
		this.postsRepository = postsRepository;
	}

	public Post createNewPost(PostForm postForm) {
		Post post = new Post();
		post.setNickname(postForm.getNickname());
		post.setTitle(postForm.getTitle());
		post.setTextContent(postForm.getTextContent());
		post.setCreatedDateTime(LocalDateTime.now());
		postsRepository.add(post);
		return post;
	}

	public List<SimplePostForm> mappingSimpleForm(List<Post> posts) {
		return posts.stream()
			.map(Post::mappingSimpleForm)
			.collect(Collectors.toList());
	}
}
