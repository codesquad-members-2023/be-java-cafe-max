package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {

    private final Long id;
    private String title;
    private String content;
    private final String author;

    public ArticleDTO(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public static ArticleDTO toArticleDTO(final Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), article.getAuthor());
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
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
