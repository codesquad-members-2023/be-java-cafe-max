package kr.codesqaud.cafe.domain;

public class Comment {
    private Long id;
    private Long articleId;
    private String content;
    private Long userId;
    private String userName;
    private String createTime;

    public Comment() {}

    public Comment(Long id, Long articleId, String content, Long userId, String userName, String createTime) {
        this.id = id;
        this.articleId = articleId;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
        this.createTime = createTime;
    }

    public void create(Long id, Comment comment) {
        this.id = id;
        this.articleId = comment.articleId;
        this.content = comment.content;
        this.userId = comment.userId;
        this.userName = comment.userName;
        this.createTime = comment.createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
