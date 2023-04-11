package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleWithSurroundingResponse {
    private Long id;
    private String title;
    private String writer;
    private String contents;
    private final LocalDateTime createdAt;
    private Long previousId;
    private Long nextId;

    private ArticleWithSurroundingResponse(Long id, String title, String writer, String contents, LocalDateTime createdAt, Long previousId, Long nextId) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.createdAt = createdAt;
        this.previousId = previousId;
        this.nextId = nextId;
    }

    public static ArticleWithSurroundingResponse from(Article article) { // Entity → DTO
        return new ArticleWithSurroundingResponse(article.getId(), article.getTitle(), article.getWriter(), article.getContents(), article.getCreatedAt(), article.getPreviousId(), article.getNextId());
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getPreviousId() { return previousId; }
    public Long getNextId() { return nextId; }
}
