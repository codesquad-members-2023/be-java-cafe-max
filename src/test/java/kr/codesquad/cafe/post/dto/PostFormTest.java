package kr.codesquad.cafe.post.dto;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PostFormTest {

    public static final long TEST_ID = 1L;
    public static final String TEST_NAME = "jack";
    public static final String TEST_TITLE = "title";
    public static final String TEXT_CONTENT = "textContent";

    @DisplayName("post객체에서 오직 타이틀과 텍스트 콘텐츠만 가져온다")
    @Test
    void from() {
        User user = mock(User.class);
        Post post = Post.builder()
                .id(TEST_ID)
                .nickname(TEST_NAME)
                .title(TEST_TITLE)
                .user(user)
                .createdDateTime(LocalDateTime.now())
                .textContent(TEXT_CONTENT)
                .build();

        PostForm postForm = PostForm.from(post);

        assertThat(postForm.getTitle()).isEqualTo(post.getTitle());
        assertThat(postForm.getTextContent()).isEqualTo(post.getTextContent());
        assertThat(postForm).hasOnlyFields("title", "textContent");
    }

    @DisplayName("user정보의 아이디와 닉네임과 PostForm의 타이틀과 텍스트 콘텐츠로 post를 만든다")
    @Test
    void toPost() {
        User user = User.builder()
                .id(TEST_ID)
                .nickname(TEST_NAME)
                .build();
        PostForm postForm = new PostForm(TEST_TITLE, TEXT_CONTENT);

        Post post = postForm.toPost(user);

        assertThat(post.getTitle()).isEqualTo(TEST_TITLE);
        assertThat(post.getTextContent()).isEqualTo(TEXT_CONTENT);
        assertThat(post.getUser()).isEqualTo(user);
        assertThat(post.getNickname()).isEqualTo(TEST_NAME);
    }
}
