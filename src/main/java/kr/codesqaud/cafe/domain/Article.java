package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Article {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdTime;

    public Article() {}

    public Article(Long id, String title, String content, String author, LocalDateTime createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}



    /*

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}

     */


/*

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdTime;

    private Article(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdTime = builder.createdTime;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdTime;
    }


    public static Builder builder(Long id, String title, String content, String author) {
        return new Builder(id, title, content, author);
    }

    public static class Builder {

        //id는 어차피 DB에서 부여하므로 final 삭제
        private Long id;
        private final String title;
        private final String content;
        private final String author;
        private LocalDateTime createdTime;

        public Builder(Long id, String title, String content, String author) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.author = author;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder createdAt(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
    }
 */