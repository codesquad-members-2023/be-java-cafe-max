package kr.codesqaud.cafe.post;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import kr.codesqaud.cafe.post.form.PostForm;

@Service
public class PostService {

	private final PostsRepository postsRepository;

	public PostService(PostsRepository postsRepository) {
		this.postsRepository = postsRepository;
	}

	public void createNewPost(PostForm postForm) {
		long newId = Post.createNewId();
		Post post = new Post(newId);
		post.setNickname(postForm.getNickname());
		post.setTitle(postForm.getTitle());
		post.setTextContent(postForm.getTextContent());
		post.setCreatedDateTime(LocalDateTime.now());
		postsRepository.add(post);
	}
}
