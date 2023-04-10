package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.post.dto.PostForm;
import kr.codesqaud.cafe.post.dto.SimplePostForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(PostForm postForm) {
        Post post = postForm.toPost();
        return postRepository.save(post);
    }

    public Optional<Post> findById(int postId) {
        return postRepository.findById((long) postId);
    }

    public List<SimplePostForm> getAllPosts() {
        return postRepository.findAll().stream()
                .map(SimplePostForm::from)
                .collect(Collectors.toList());
    }
}
