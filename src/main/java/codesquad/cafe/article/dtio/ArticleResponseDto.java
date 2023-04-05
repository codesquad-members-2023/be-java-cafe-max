package codesquad.cafe.article.dtio;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String date;

    public ArticleResponseDto(final Long id, final String writer, final String title, final String contents, final String localDateTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.date = localDateTime;
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

    public String getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }
}
