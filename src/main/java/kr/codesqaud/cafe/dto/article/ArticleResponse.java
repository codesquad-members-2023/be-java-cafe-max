package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleResponse {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private final LocalDateTime createdAt;

    private ArticleResponse(Long id, String title, String writer, String contents, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public static ArticleResponse from(Article article) { // Entity → DTO
        return new ArticleResponse(article.getId(), article.getTitle(), article.getWriter(), article.getContents(), article.getCreatedAt());
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
