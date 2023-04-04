package kr.codesqaud.cafe.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

	public static final AtomicLong atomicKey = new AtomicLong();
	private final List<Post> usersRepository;

	public PostRepository() {
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

	public Optional<Post> findByTitle(String title) {
		return usersRepository.stream()
			.filter(post -> Objects.equals(post.getTitle(), title))
			.findAny();
	}

	public void clear() {
		usersRepository.clear();
	}
}
