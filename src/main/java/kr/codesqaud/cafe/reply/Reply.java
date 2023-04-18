package kr.codesqaud.cafe.reply;

/**
 * 댓글을 의미하는 객체
 */
public class Reply {

    private long id;
    private long articleId;
    private String userId;
    private String contents;
    private String createDateTime;

    public Reply(long articleId, String userId, String contents) {
        this.articleId = articleId;
        this.userId = userId;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }
}
