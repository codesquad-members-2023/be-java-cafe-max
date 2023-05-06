package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.global.exception.UnauthorizedAccessException;
import kr.codesquad.cafe.post.exception.DeletionFailedException;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PostTest {

    public static final long TEST_ID = 1L;
    public static final long NOT_MATCH_ID = 2L;
    public static final long OTHER_ID = 2L;
    public static final String TEXT_TITLE = "title";
    public static final String TEXT_CONTENT = "content";
    public static final String TARGET_CONTENT = "targetContent";
    public static final String TARGET_TITLE = "targetTitle";

    @DisplayName("post Delete상태로 변경한다")
    @Test
    void disable() {
        Post post = new Post();
        assertThat(post.isDeleted()).isFalse();

        post.disable();

        assertThat(post.isDeleted()).isTrue();
    }

    @DisplayName("Comment를 추가한다")
    @Test
    void addComment() {
        Post post = new Post();
        Comment comment = new Comment();
        List<Comment> comments = post.getComments();
        assertThat(comments).doesNotContain(comment);

        post.addComment(comment);

        assertThat(comments).contains(comment);
    }

    @DisplayName("유저의 댓글만 존재할 때 삭제 가능")
    @Test
    void deleteSuccess() {
        User user = User.builder().id(TEST_ID).build();
        Post post = Post.builder()
                .user(user)
                .build();
        Comment comment = Comment.build()
                .user(user)
                .build();
        post.addComment(comment);
        assertThat(post.getComments()).contains(comment);

        assertThatCode(post::delete).doesNotThrowAnyException();
        assertThat(post.isDeleted()).isTrue();
    }

    @DisplayName("유저의 댓글만 존재할 때 삭제 가능")
    @Test
    void deleteFailedByOtherUserComment() {
        User user = User.builder().id(TEST_ID).build();
        User otherUser = User.builder().id(OTHER_ID).build();
        Post post = Post.builder()
                .user(user)
                .build();
        Comment comment = Comment.build()
                .user(otherUser)
                .build();
        post.addComment(comment);

        assertThatThrownBy(post::delete).isInstanceOf(DeletionFailedException.class);
    }

    @DisplayName("포스트 유저 아이디와 일치 하지 않을 때 에러 발생")
    @Test
    void checkPermissionFailed() {
        User user1 = User.builder()
                .id(TEST_ID)
                .build();

        Post post = Post.builder()
                .user(user1)
                .build();

        assertThatThrownBy(() -> post.checkPermission(NOT_MATCH_ID)).isInstanceOf(UnauthorizedAccessException.class);
    }

    @DisplayName("포스트 유저 아이디와 일치 할 때 아무일도 발생하지 않는다")
    @Test
    void checkPermission() {
        User user1 = User.builder()
                .id(TEST_ID)
                .build();

        Post post = Post.builder()
                .user(user1)
                .build();

        assertThatCode(() -> post.checkPermission(TEST_ID)).doesNotThrowAnyException();
    }

    @DisplayName("포스트의 타이틀과 내요을 업로드한다")
    @Test
    void update() {
        Post post = Post.builder()
                .title(TEXT_TITLE)
                .textContent(TEXT_CONTENT)
                .build();

        post.update(TARGET_CONTENT, TARGET_TITLE);

        assertThat(post.getTitle()).isEqualTo(TARGET_TITLE);
        assertThat(post.getTextContent()).isEqualTo(TARGET_CONTENT);
    }
}
