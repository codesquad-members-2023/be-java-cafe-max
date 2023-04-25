package codesquad.cafe.article.domain;

import codesquad.cafe.article.dto.ArticleResponseDto;
import codesquad.cafe.article.dto.ArticleUpdateRequestDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private Long id;
    private String writerId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;


    public Article(final Long id, final String title, final String contents, final LocalDateTime createdAt, final String writerId) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.writerId = writerId;
    }

    public Article(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getWriterId() {
        return writerId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Article createdWith(final long id) {
        this.id = id;
        return this;
    }

    public ArticleResponseDto toDto(String writer) {
        return new ArticleResponseDto(id, writer, title, contents, createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    public Article update(final ArticleUpdateRequestDto articleUpdateRequestDto) {
        this.title = articleUpdateRequestDto.getTitle();
        this.contents = articleUpdateRequestDto.getContents();
        return this;
    }
}
