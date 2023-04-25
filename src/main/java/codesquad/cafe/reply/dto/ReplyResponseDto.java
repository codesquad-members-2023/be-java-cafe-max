package codesquad.cafe.reply.dto;


public class ReplyResponseDto {
    private Long id;
    private String contents;
    private String createdAt;
    private String userId;
    private Long postId;
    private String userName;

    public ReplyResponseDto(final Long id, final String contents, final String createdAt, final String userId, final Long postId, final String userName) {
        this.id = id;
        this.contents = contents;
        this.createdAt = createdAt;
        this.userId = userId;
        this.postId = postId;
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
