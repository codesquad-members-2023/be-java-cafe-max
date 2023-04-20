package codesquad.cafe.domain.reply.domain;

import codesquad.cafe.domain.reply.dto.ReplyRequestDto;
import codesquad.cafe.domain.user.domain.User;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private String userId;
    private Long postId;

    public Reply(final Long postId, final User user, final ReplyRequestDto replyRequestDto) {
        this.contents = replyRequestDto.getReplyContents();
        this.createdAt = LocalDateTime.now();
        this.userId = user.getId();
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }
}
