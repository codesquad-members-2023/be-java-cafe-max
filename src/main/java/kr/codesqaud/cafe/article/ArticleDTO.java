package kr.codesqaud.cafe.article;

import java.math.BigInteger;
import java.sql.Timestamp;

public class ArticleDTO {
    private BigInteger articleId;
    private String writer;
    private String title;
    private String contents;
    private String createdAt;

    public ArticleDTO() {
    }

    public BigInteger getArticleId() {
        return articleId;
    }

    public void setArticleId(BigInteger articleId) {
        this.articleId = articleId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
