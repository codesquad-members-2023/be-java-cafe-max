package kr.codesqaud.cafe.dto;

import java.time.LocalDateTime;

public class ArticleResponseDto {
    private Integer id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime writeTime;

    public ArticleResponseDto(final Integer id, final String writer, final String title, final String contents, final LocalDateTime writeTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
    }

    public Integer getId() {
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

    public LocalDateTime getWriteTime() {
        return writeTime;
    }
}
