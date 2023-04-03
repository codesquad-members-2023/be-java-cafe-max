package kr.codesqaud.cafe.article;

public class Article {
    private final String writer;
    private final String title;
    private final String content;

    public Article(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
