package kr.codesqaud.cafe.article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 게시글 엔티티
 */
public class Article {
    private static int idCount = 0;
    private final int articleId;
    private final String writer;
    private final String title;
    private final String contents;
    private final String createdAt;

    public Article(String writer, String title, String contents) {
        this.articleId = ++idCount;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public int getArticleId() {
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

    @Override
    public String toString() {
        return "Article{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", content='" + contents + '\'' +
                '}';
    }
}
