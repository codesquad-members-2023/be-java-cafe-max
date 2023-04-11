package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.ArticleResponseDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public Article(final String writer, final String title, final String contents, final LocalDateTime writeTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public Article(final Long id, final String writer, final String title, final String contents, final LocalDateTime writeTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getWriteTime() {
        return writeTime;
    }

    public String getContents() {
        return contents;
    }

    public ArticleResponseDto toDto() {
        return new ArticleResponseDto(id, writer, title, contents, writeTime.format(DateTimeFormatter.ofPattern("YYYY-MM-DD HH:mm")));

    }
}
