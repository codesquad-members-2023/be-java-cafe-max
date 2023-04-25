package codesquad.cafe.reply.domain;

import codesquad.cafe.reply.dto.ReplyRequestDto;
import codesquad.cafe.reply.dto.ReplyResponseDto;
import codesquad.cafe.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Reply(final Long id, final String contents, final LocalDateTime createdAt, final String userId, final Long postId) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.userId = userId;
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

    public ReplyResponseDto toDto(final String userName) {
        return new ReplyResponseDto(
                this.id,
                this.contents,
                this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                this.userId,
                this.postId,
                userName);
    }
}
