package kr.codesqaud.cafe.domain;

public class Article {

    private Long id;
    private final String title;
    private final String content;
    private final String author;
    private final String createdTime;
    private final String updatedTime;

    private Article(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdTime = builder.createdTime;
        this.updatedTime = builder.updatedTime;
    }

    public Long getId() {
        return id;
    }

    // todo : 이걸 어떻게 하지...
    public void setId(Long id) {
        this.id = id;
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

    public String getCreatedAt() {
        return createdTime;
    }

    public String getUpdatedAt() {
        return updatedTime;
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
        private String createdTime;
        private String updatedTime;

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

        public Builder createdAt(String createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder updatedAt(String updatedTime) {
            this.updatedTime = updatedTime;
            return this;
        }

        public Article build() {
            return new Article(this);
        }
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
