package codesquad.cafe.domain;

import java.time.LocalDateTime;

public class Article {

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
}
