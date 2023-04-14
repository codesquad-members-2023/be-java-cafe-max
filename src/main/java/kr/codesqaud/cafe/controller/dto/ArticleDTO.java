package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

import java.time.LocalDateTime;

public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdTime;

    public ArticleDTO() {}

    public ArticleDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdTime = builder.createdTime;
    }

    public Article toEntity() {
        return new Article(id, title, content, author, createdTime);
    }

    public static ArticleDTO from(final Article article) {
        return new ArticleDTO.Builder(article.getId(), article.getTitle(), article.getContent(), article.getAuthor())
                .createdAt(article.getCreatedTime())
                .build();
    }

    public static class Builder {
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
            this.createdTime = LocalDateTime.now();
        }

        public Builder createdAt(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public ArticleDTO build() {
            return new ArticleDTO(this);
        }
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    //post 수정 시 입력값을 받아서 객체 생성하므로 public 선언
//    public ArticleDTO(Long id, String title, String content, String author, LocalDateTime createdTime) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.author = author;
//        this.createdTime = createdTime;
//    }
//
//    public Article toEntity() {
//        return new Article(id, title, content, "익명", createdTime);
//    }
//
//    public static ArticleDTO from(final Article article) {
//        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), "익명", article.getCreatedTime());
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public LocalDateTime getCreatedTime() {
//        return createdTime;
//    }
}


/*
    계속 id nullPointException 떠서 그냥 지움

    public ArticleDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdTime = builder.createdTime;
    }

    public Article toEntity() {
        return new Article(id, title, content, author, createdTime);
    }

    public static ArticleDTO from(final Article article) {
        return new ArticleDTO.Builder(article.getId(), article.getTitle(), article.getContent(), article.getAuthor())
                .createdAt(article.getCreatedTime())
                .build();
    }

    public static class Builder {
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
            this.createdTime = LocalDateTime.now();
        }

        public Builder createdAt(LocalDateTime createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public ArticleDTO build() {
            return new ArticleDTO(this);
        }
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

 */
