package kr.codesqaud.cafe.dto.article;

import java.time.LocalDateTime;

public class ArticleResponseDto {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String writeTime;

    public ArticleResponseDto(final Long id, final String writer, final String title, final String contents, final String writeTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
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

    public String getWriteTime() {
        return writeTime;
    }
}
