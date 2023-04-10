package codesquad.cafe.domain.article.domain;

import codesquad.cafe.domain.article.dto.ArticleResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdAt;


    public Article(final Long id, final String writer, final String title, final String contents, final LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public Article(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public Article createdWith(final long id) {
        this.id = id;
        return this;
    }

    public ArticleResponseDto toDto() {
        return new ArticleResponseDto(id, writer, title, contents, createdAt.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")));
    }
}
