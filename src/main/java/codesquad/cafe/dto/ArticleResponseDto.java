package codesquad.cafe.dto;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleResponseDto {

    private AtomicLong id;
    private String writer;
    private String title;
    private String date;

    public ArticleResponseDto(final AtomicLong id, final String writer, final String title, final String localDateTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.date = localDateTime;
    }

    public AtomicLong getId() {
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
}
