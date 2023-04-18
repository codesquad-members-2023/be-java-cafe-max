package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.comment.domain.Comment;
import kr.codesqaud.cafe.comment.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {
    private static final long ARTICLE_ID = 1;
    private static final String CONTENTS = "Test Comment";
    private static final String USER_ID = "JohnDoe";
    private static final LocalDateTime CREATED_TIME = LocalDateTime.now().withNano(0);

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("새로운 댓글을 저장시 id로 찾을 수 있다")
    public void test_save() {
        // given
        Comment comment = new Comment(ARTICLE_ID, CONTENTS, USER_ID, CREATED_TIME);

        // when
        long savedId = commentRepository.save(comment);

        // then
        Comment savedComment = commentRepository.findById(savedId);
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getArticleId()).isEqualTo(ARTICLE_ID);
        assertThat(savedComment.getContents()).isEqualTo(CONTENTS);
        assertThat(savedComment.getUserId()).isEqualTo(USER_ID);
        assertThat(savedComment.getCreatedTime()).isEqualTo(CREATED_TIME);
    }
}
