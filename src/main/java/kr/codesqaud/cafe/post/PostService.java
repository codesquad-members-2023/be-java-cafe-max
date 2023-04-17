package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.dto.PostForm;
import kr.codesqaud.cafe.post.dto.SimplePostForm;
import kr.codesqaud.cafe.post.exception.IllegalPostIdException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {


    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(PostForm postForm, User user) {
        Post post = postForm.toPost(user);
        return postRepository.save(post);
    }

    public Post findById(long postId) {
        return postRepository.findByIdAndAndIsDeleted(postId, false).orElseThrow(IllegalPostIdException::new);
    }

    public List<SimplePostForm> getAllPosts() {
        return postRepository.findAllByIsDeleted(false).stream()
                .map(SimplePostForm::from)
                .collect(Collectors.toList());
    }

    public Post updateFromPostForm(Post target, PostForm postForm) {
        Post post = postRepository.findById(target.getId()).orElseThrow(RuntimeException::new);
        postForm.editPost(post);
        return postRepository.save(post);
    }

    public void delete(Post post) {
        post.disable();
        postRepository.save(post);
    }
}
