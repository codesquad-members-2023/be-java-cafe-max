package kr.codesqaud.cafe.domain;

import java.sql.Timestamp;

public class Comment {
    private Long id;
    private Long reid;
    private Long articleId;
    private String content;
    private String commenter;
    private Timestamp createTime;

    public Comment(Long id, Long reid, Long articleId, String content, String commenter, Timestamp createTime) {
        this.id = id;
        this.reid = reid;
        this.articleId = articleId;
        this.content = content;
        this.commenter = commenter;
        this.createTime = createTime;
    }

    public void create(Long id, Comment comment) {
        this.id = id;
        this.reid = comment.reid;
        this.articleId = comment.articleId;
        this.content = comment.content;
        this.commenter = comment.commenter;
        this.createTime = comment.createTime;
    }

    public Long getId() {
        return id;
    }

    public Long getReid() {
        return reid;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getContent() {
        return content;
    }

    public String getCommenter() {
        return commenter;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
