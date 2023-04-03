package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.post.form.PostForm;
import kr.codesqaud.cafe.post.form.SimplePostForm;

@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post createNewPost(PostForm postForm) {
		Post post = new Post.Builder(PostRepository.atomicKey.incrementAndGet())
			.nickname(postForm.getNickname())
			.title(postForm.getTitle())
			.textContent(postForm.getTextContent())
			.createdDateTime(LocalDateTime.now())
			.build();
		postRepository.add(post);
		return post;
	}

	public List<SimplePostForm> mappingSimpleForm(List<Post> posts) {
		return posts.stream()
			.map(SimplePostForm::from)
			.collect(Collectors.toList());
	}

	public Optional<Post> findById(Long postId) {
		return postRepository.findById(postId);
	}
}
