package kr.codesqaud.cafe.domain;

public class Article {
    private Long articleIndex;
    private String writer;
    private String title;
    private String contents;

    public Article() {
    }

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public String getWriter() {
        return writer;
    }

    public Long getArticleIndex() {
        return articleIndex;
    }

    public void setArticleIndex(Long articleIndex) {
        this.articleIndex = articleIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
