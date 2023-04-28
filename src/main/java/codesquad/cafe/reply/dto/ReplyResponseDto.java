package codesquad.cafe.reply.dto;


import codesquad.cafe.reply.domain.Reply;

import java.time.format.DateTimeFormatter;

public class ReplyResponseDto {
    private Long id;
    private String contents;
    private String createdAt;
    private String userId;
    private Long postId;
    private String userName;

    public ReplyResponseDto(final Reply reply, final String userName) {
        this.id = reply.getId();
        this.contents = reply.getContents();
        this.createdAt = reply.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.userId = reply.getUserId();
        this.postId = reply.getPostId();
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getUserName() {
        return userName;
    }
}
