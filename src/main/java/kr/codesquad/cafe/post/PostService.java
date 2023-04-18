package kr.codesquad.cafe.post;

import kr.codesquad.cafe.user.User;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import kr.codesquad.cafe.post.exception.IllegalPostIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostService {


    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
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

    @Transactional
    public Post updateFromPostForm(Post post, PostForm postForm) {
        post.setFrom(postForm);
        return postRepository.save(post);
    }

    @Transactional
    public void delete(Post post) {
        post.disable();
        postRepository.save(post);
    }
}
