package codesquad.cafe.article.domain;

import java.time.LocalDateTime;

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

    public Article update(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
        return this;
    }
}
