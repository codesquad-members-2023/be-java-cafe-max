package kr.codesqaud.cafe.dto.comment;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.Member;
import org.hibernate.validator.constraints.Length;

public class CommentWriteRequest {

    private Long postId;

    private WriterRequest writer;

    @NotBlank(message = "내용은 필수입니다.")
    @Length(min = 2, max = 3000, message = "최소 {min}글자, 최대 {max}글자입니다.")
    private String content;

    private LocalDateTime writeDateTime;

    private CommentWriteRequest() {
        this.writeDateTime = LocalDateTime.now();
    }

    public CommentWriteRequest(Long postId, WriterRequest writer, String content) {
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.writeDateTime = LocalDateTime.now();
    }

    public Long getPostId() {
        return postId;
    }

    public WriterRequest getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public void initializeWriterAndPostId(AccountSession accountSession, Long postId) {
        this.writer = new WriterRequest(accountSession.getId(), accountSession.getName());
        this.postId = postId;
    }

    public Comment toComment(Member member) {
        return Comment.builder()
            .postId(postId)
            .writer(member)
            .content(content)
            .writeDate(writeDateTime)
            .build();
    }
}
