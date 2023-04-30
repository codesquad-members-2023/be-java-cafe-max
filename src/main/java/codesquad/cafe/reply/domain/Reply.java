package codesquad.cafe.reply.domain;

import codesquad.cafe.user.domain.User;

import java.time.LocalDateTime;

public class Reply {

    private Long id;
    private String contents;
    private LocalDateTime createdAt;
    private String userId;
    private Long postId;

    public Reply(final Long postId, final User user, final String replyContents) {
        this.contents = replyContents;
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

}
