package kr.codesquad.cafe.post;

import kr.codesquad.cafe.user.User;
import kr.codesquad.cafe.user.UserService;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.exception.IllegalPostIdException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    private static final String JACK = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    public static final long NO_EXIST_ID = 100L;
    public static final String TEST_TITLE = "test_title";
    public static final String TEST_CONTENT = "test_content";
    public static final String TARGET_TITLE = "target_title";
    public static final String TARGET_CONTENT = "target_content";

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    User jack;

    @BeforeEach
    void setJack() {
        jack = userService.save(new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD));
    }

    @DisplayName("새 포스트 저장")
    @Test
    void save() {
        List<Post> before = postRepository.findAll();
        Post post = postService.save(new PostForm(TEST_TITLE, TEST_CONTENT), jack);
        List<Post> after = postRepository.findAll();
        assertThat(before.stream()
                .anyMatch(postElement -> Objects.equals(postElement.getId(), post.getId()))).isFalse();
        assertThat(after.stream()
                .anyMatch(postElement -> Objects.equals(postElement.getId(), post.getId()))).isTrue();
    }

    @DisplayName("id로 Post 찾기")
    @Test
    void findById() {
        Post post = postService.save(new PostForm(TEST_TITLE, TEST_CONTENT), jack);
        assertThatThrownBy(() -> postService.findById(NO_EXIST_ID)).isInstanceOf(IllegalPostIdException.class);
        assertThatCode(() -> postService.findById(post.getId())).doesNotThrowAnyException();
        post.disable();
        assertThatThrownBy(() -> postService.findById(post.getId())).isInstanceOf(IllegalPostIdException.class);
    }

    @DisplayName("모든 Post 찾기")
    @Test
    void getAllPosts() {
        List<Post> before = postRepository.findAll();
        int beforeSize = before.size();
        Post post = postService.save(new PostForm(TEST_TITLE, TEST_CONTENT), jack);
        List<Post> after = postRepository.findAll();
        int afterSize = after.size();
        SoftAssertions.assertSoftly(softAssertions -> {
            assertThat(afterSize - beforeSize).isEqualTo(1);
            assertThat(before.stream()
                    .anyMatch(postElement -> Objects.equals(postElement.getId(), post.getId()))).isFalse();
            assertThat(after.stream()
                    .anyMatch(postElement -> Objects.equals(postElement.getId(), post.getId()))).isTrue();
        });
    }

    @DisplayName("post 업데이트(성공)")
    @Test
    void updateFromPostFormSuccess() {
        Post post = postService.save(new PostForm(TEST_TITLE, TEST_CONTENT), jack);
        Post savePost = postService.updateFromPostForm(post.getId(), new PostForm(TARGET_TITLE, TARGET_CONTENT));
        assertThat(savePost.getTitle()).isEqualTo(TARGET_TITLE);
        assertThat(savePost.getTextContent()).isEqualTo(TARGET_CONTENT);
        Post DBpost = postService.findById(post.getId());
        assertThat(DBpost.getTitle()).isEqualTo(TARGET_TITLE);
        assertThat(DBpost.getTextContent()).isEqualTo(TARGET_CONTENT);
    }

    @DisplayName("Post를 삭제")
    @Test
    void delete() {
        Post post = postService.save(new PostForm(TEST_TITLE, TEST_CONTENT), jack);
        assertThatCode(() -> postService.findById(post.getId())).doesNotThrowAnyException();
        postService.delete(post.getId());
        assertThatThrownBy(() -> postService.findById(post.getId())).isInstanceOf(IllegalPostIdException.class);
    }
}
