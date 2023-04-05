package codesquad.cafe.article.domain;

import codesquad.cafe.article.dtio.ArticleResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime date;

    public Article(final String writer, final String title, final String contents, final LocalDateTime date) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = date;
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

    public LocalDateTime getDate() {
        return date;
    }

    public Article createdWith(final long id) {
        this.id = id;
        return this;
    }

    public ArticleResponseDto toDto() {
        return new ArticleResponseDto(id, writer, title, contents, date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm")));
    }
}
