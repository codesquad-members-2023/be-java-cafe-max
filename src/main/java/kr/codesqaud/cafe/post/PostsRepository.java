package kr.codesqaud.cafe.post;

import java.util.ArrayList;
import java.util.List;

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
}
