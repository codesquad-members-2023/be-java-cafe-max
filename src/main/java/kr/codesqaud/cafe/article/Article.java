package kr.codesqaud.cafe.article;

import kr.codesqaud.cafe.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 게시글 엔티티
 */
public class Article {
    private final Logger logger = LoggerFactory.getLogger(Article.class);
    private BigInteger articleId;
    private String writer;
    private String title;
    private String contents;
    private String createdAt;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        saveCurrentTime();
    }

    private void saveCurrentTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TimeZone timeZone = TimeZone.getTimeZone("Seoul/Korea");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        this.createdAt = sdf.format(timestamp);
        logger.info(createdAt);
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

    public String getCreatedAt() {
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
