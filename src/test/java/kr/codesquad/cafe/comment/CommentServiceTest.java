package kr.codesquad.cafe.comment;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("댓글 저장은 repository 에 위임한다")
    @Test
    void save() {
        User user = mock(User.class);
        Post post = mock(Post.class);
        Comment comment = mock(Comment.class);
        given(commentRepository.save(any(Comment.class))).willReturn(comment);

        Comment save = commentService.save("testContent", post, user);

        verify(commentRepository).save(any(Comment.class));
        assertThat(save).usingRecursiveComparison().isEqualTo(comment);
    }

    @DisplayName("존재하지 않는 댓글을 삭제시 에러를 던진다.")
    @Test
    void deleteFailedByCommentNotFound() {
        User user = mock(User.class);
        Comment comment = mock(Comment.class);
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.delete(comment.getId(), user.getId())).isInstanceOf(CommentNotFoundException.class);
        verify(commentRepository, times(1)).findById(any(Long.class));
    }

    @DisplayName("댓글을 정상적으로 삭제시 Comment.delete() 호출하여 삭제한다.")
    @Test
    void delete() {
        User user = mock(User.class);
        Comment comment = mock(Comment.class);
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));

        commentService.delete(comment.getId(), user.getId());

        verify(commentRepository, times(1)).findById(any(Long.class));
        verify(comment, times(1)).delete(anyLong());
    }
}
