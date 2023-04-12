package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String createdTime;
    private final String updatedTime;

    //post 수정 시 입력값을 받아서 객체 생성하므로 public 선언
    public ArticleDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdTime = builder.createdTime;
        this.updatedTime = builder.updatedTime;
    }

    public Article toEntity() {
        return Article.builder(id, title, content, author)
                .createdAt(createdTime)
                .updatedAt(updatedTime)
                .build();
    }

    public static ArticleDTO from(final Article article) {
        return new ArticleDTO.Builder(article.getId(), article.getTitle(), article.getContent(), article.getAuthor())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
    }

    public static class Builder {
        private final Long id;
        private final String title;
        private final String content;
        private final String author;
        private String createdTime;
        private String updatedTime;

        public Builder(Long id, String title, String content, String author) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.author = author;
        }

        public Builder createdAt(String createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder updatedAt(String updatedTime) {
            this.updatedTime = updatedTime;
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

    public String getCreatedTime() {
        return createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }
}


/*

        public static ArticleDTO toArticleDTO(final Article article) {
            return new Builder(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .build();
        }

        public static class Builder {
        //필수 매개변수
        private final long id;

        //선택 매개변수
        private String title;
        private String content;
        private String author;
        private Timestamp createdAt;
        private Timestamp updatedAt;

        public Builder(long id) {
            this.id = id;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt == null ? null : Timestamp.valueOf(createdAt);
            return this;
        }

        public Builder updatedAt(String updatedAt) {
            this.updatedAt = updatedAt == null ? null : Timestamp.valueOf(updatedAt);
            return this;
        }


        public ArticleDTO build() {
            return new ArticleDTO(this);
        }
    }
 */
