package kr.codesqaud.cafe.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class PostsRepository {

	private final List<Post> usersRepository;

	public PostsRepository() {
		this.usersRepository = new ArrayList<>();
	}

	public boolean add(Post post) {
		return usersRepository.add(post);
	}

	public boolean remove(Post post) {
		return usersRepository.remove(post);
	}

	public List<Post> getAllPosts() {
		return new ArrayList<>(usersRepository);
	}

	public Optional<Post> findById(Long postId) {
		return usersRepository.stream()
			.filter(post -> Objects.equals(post.getId(), postId))
			.findAny();
	}
}
