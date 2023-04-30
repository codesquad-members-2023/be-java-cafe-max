package codesquad.cafe.article.dto;

import codesquad.cafe.article.domain.Article;

import java.time.format.DateTimeFormatter;

public class ArticleResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String createdAt;

    public ArticleResponseDto(final Long id, final String writer, final String title, final String contents, final String createdAt) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
    }

    public ArticleResponseDto(final Article article, final String writerName) {
        this.id = article.getId();
        this.writer = writerName;
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.createdAt = article.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Long getId() {
        return id;
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
}
