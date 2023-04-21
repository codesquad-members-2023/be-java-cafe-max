package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDto {
    private final Long id;
    private final String writer;
    private final String title;
    private final String content;
    private final String createdAt;

    public ArticleDto(Long id, String writer, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdAt = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm").format(createdAt);
    }


    public static ArticleDto from(final Article article) {
        return new ArticleDto(article.getId(), article.getWriter(), article.getTitle(), article.getContent(), article.getCreatedAt());
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
