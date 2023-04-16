package kr.codesqaud.cafe.domain.entity;

public class Article {
    private long articleId;
    private String writer;
    private String title;
    private String contents;

    public Article(long articleId, String writer, String title, String contents) {
        this.articleId = articleId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public long getArticleId() {
        return articleId;
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

}
