package kr.codesqaud.cafe.controller.dto.comment;

import kr.codesqaud.cafe.domain.Comment;

public class CommentDTO {
    private final Long id;
    private final Long articleId;
    private final String content;
    private final Long userId;
    private final String userName;
    private final String createTime;

    public CommentDTO(Long id, Long articleId, String content, Long userId, String userName, String createTime) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createTime = createTime;
    }

    public static CommentDTO from(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getArticleId(), comment.getContent(), comment.getUserId(), comment.getUserName(), comment.getCreateTime());
    }

    public Comment toEntity(Long articleId, Long userId, String userName) {
        return new Comment(id, articleId, content, userId, userName, createTime);
    }

    public Long getId() {
        return id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }

    public String getCreateTime() {
        return createTime;
    }
}
