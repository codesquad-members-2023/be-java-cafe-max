package kr.codesqaud.cafe.domain;

public class Article {
    private String writer;
    private String title;
    private String contents;

    public Article(String writer, String title, String contents){
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }
}
