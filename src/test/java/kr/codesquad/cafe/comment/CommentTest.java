package kr.codesquad.cafe.comment;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {

    public static final String TEXT_COMMENT = "textComment";
    public static final long TEST_ID = 1L;
    public static final long OTHER_ID = 2L;

    @DisplayName("유저,포스트,텍스트 콘텐츠를 받고 댓글객체를 만든다")
    @Test
    void from() {
        User user = new User();
        Post post = new Post();

        Comment comment = Comment.from(TEXT_COMMENT, post, user);

        assertThat(comment.getContent()).isEqualTo(TEXT_COMMENT);
        assertThat(comment.getPost()).isEqualTo(post);
        assertThat(comment.getUser()).isEqualTo(user);
    }

    @DisplayName("댓글 유저 아이디와 일치한 아이디면 정상적으로 삭제한다")
    @Test
    void delete() {
        User user = getTestUser();
        Comment comment = getTestComment(user);
        assertThat(comment.isDeleted()).isFalse();

        comment.delete(TEST_ID);

        assertThat(comment.isDeleted()).isTrue();
    }

    @DisplayName("댓글 유저 아이디와 일치하지 않는 아이디면 에러가 발생한다")
    @Test
    void deleteFailed() {
        User user = getTestUser();
        Comment comment = getTestComment(user);
        assertThat(comment.isDeleted()).isFalse();

        assertThatThrownBy(() -> comment.delete(OTHER_ID))
                .isInstanceOf(UnauthorizedDeleteCommentException.class);
    }

    @DisplayName("유저아이디를 동일한지 확인한다")
    @Test
    void isSameId() {
        User user = getTestUser();
        Comment comment = getTestComment(user);
        assertThat(comment.isSameUserId(OTHER_ID)).isFalse();
        assertThat(comment.isSameUserId(TEST_ID)).isTrue();
    }

    private static Comment getTestComment(User user) {
        return Comment.build()
                .user(user)
                .build();
    }

    private static User getTestUser() {
        return User.builder()
                .id(TEST_ID)
                .build();
    }
}
