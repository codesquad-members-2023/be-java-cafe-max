package kr.codesqaud.cafe.dto.comment;

import java.time.LocalDateTime;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.domain.Comment;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.post.WriterResponse;

public class CommentWriteRequest {

    private Long postId;

    private WriterResponse writer;

    private String content;

    private LocalDateTime writeDate;

    private Boolean isDeleted;

    private CommentWriteRequest() {
        this.writeDate = LocalDateTime.now();
        this.isDeleted = false;
    }

    public CommentWriteRequest(Long postId, String content) {
        this.postId = postId;
        this.content = content;
        this.writeDate = LocalDateTime.now();
        this.isDeleted = false;
    }

    public Long getPostId() {
        return postId;
    }

    public WriterResponse getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void initializeWriterAndPostId(AccountSession accountSession, Long postId) {
        this.writer = new WriterResponse(accountSession.getId(), accountSession.getName());
        this.postId = postId;
    }

    public Comment toComment(Member member) {
        return Comment.builder()
            .postId(postId)
            .writer(member)
            .content(content)
            .writeDate(writeDate)
            .isDeleted(isDeleted)
            .build();
    }
}
