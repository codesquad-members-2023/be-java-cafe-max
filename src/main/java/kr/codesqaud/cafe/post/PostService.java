package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.dto.PostForm;
import kr.codesqaud.cafe.post.dto.SimplePostForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Post findById(int postId) {
        return postRepository.findById((long) postId).orElseThrow(RuntimeException::new);
    }

    public List<SimplePostForm> getAllPosts() {
        return postRepository.findAll().stream()
                .map(SimplePostForm::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public Post updateFromPostForm(Post target, PostForm postForm) {
        Post post = postRepository.findById(target.getId()).orElseThrow(RuntimeException::new);
        postForm.editPost(post);
        return postRepository.save(post);
    }

    public void checkId(User user, Long id) {
        if (!user.isSameId(id)) {
            throw new RuntimeException();
        }
    }
}
