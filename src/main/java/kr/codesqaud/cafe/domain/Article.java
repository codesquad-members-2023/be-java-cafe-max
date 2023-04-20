package kr.codesqaud.cafe.domain;

public class Article {
    private long articleNum;
    private final String writer;
    private String title;
    private String contents;
    private String createdTime;

    public Article(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public void setArticleNum(long articleNum) {
        this.articleNum = articleNum;
    }
    public void setCreatedTime(String createdTime) { this.createdTime = createdTime; }


    public long getArticleNum() {
        return articleNum;
    }
}
