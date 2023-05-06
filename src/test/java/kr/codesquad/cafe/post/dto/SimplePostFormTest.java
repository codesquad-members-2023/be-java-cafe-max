package kr.codesquad.cafe.post.dto;

import kr.codesquad.cafe.post.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimplePostFormTest {

    public static final int LONG_COUNT = 100;
    public static final long TEST_ID = 1L;
    public static final String TEST_TITLE = "title";
    public static final String TEST_NICKNAME = "jack";
    public static final String SHORT_TEXT_CONTENT = "content";
    public static final int MAX_LENGTH = 150;
    public static final int BEGIN_INDEX = 0;

    private static SimplePostForm getSimplePostForm(LocalDateTime now, String textContent) {
        return new SimplePostForm.Builder()
                .id(TEST_ID)
                .nickname(TEST_NICKNAME)
                .title(TEST_TITLE)
                .textContent(textContent)
                .createdDateTime(now)
                .build();
    }

    private static Post getPost(LocalDateTime now, String textContent) {
        return Post.builder()
                .id(TEST_ID)
                .nickname(TEST_NICKNAME)
                .title(TEST_TITLE)
                .textContent(textContent)
                .createdDateTime(now)
                .build();
    }

    @DisplayName("Content 길이가 Max 길이보다 작으면 Content를 그대로 담고 및 필요한 모든 정보를 post에서 가져온다")
    @Test
    void toSimplePostFormWithShortTextContent() {
        LocalDateTime now = LocalDateTime.now();
        Post post = getPost(now, SHORT_TEXT_CONTENT);

        List<SimplePostForm> simplePostForms = SimplePostForm.toSimplePostForm(List.of(post));

        SimplePostForm simplePostForm = getSimplePostForm(now, SHORT_TEXT_CONTENT);
        assertThat(simplePostForms).contains(simplePostForm);
    }

    @DisplayName("Content 길이가 Max 길이보다 커면 Content를 잘라서 담고 및 필요한 모든 정보를 post에서 가져온다")
    @Test
    void toSimplePostFormWithLongTextContent() {
        LocalDateTime now = LocalDateTime.now();
        String longTextContent = "content".repeat(LONG_COUNT);
        Post post = getPost(now, longTextContent);

        List<SimplePostForm> simplePostForms = SimplePostForm.toSimplePostForm(List.of(post));

        SimplePostForm simplePostForm = getSimplePostForm(now, longTextContent.substring(BEGIN_INDEX, MAX_LENGTH));
        assertThat(simplePostForms).contains(simplePostForm);
    }
}
