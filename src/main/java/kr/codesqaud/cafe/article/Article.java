package kr.codesqaud.cafe.article;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 게시글 엔티티
 */
public class Article {
    private BigInteger articleId;
    private String writer;
    private String title;
    private String contents;
    private Timestamp createdAt;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public BigInteger getArticleId() {
        return articleId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setArticleId(BigInteger articleId) {
        this.articleId = articleId;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
