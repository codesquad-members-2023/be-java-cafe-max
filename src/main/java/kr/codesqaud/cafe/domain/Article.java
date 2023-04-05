package kr.codesqaud.cafe.domain;

import java.time.LocalTime;

public class Article {
    private String writer;
    private String title;
    private String contents;
    private LocalTime writtenTime;

    public void setWithArticleForm(ArticleForm articleForm) {
        this.writer = articleForm.getWriter();
        this.title = articleForm.getTitle();
        this.contents = articleForm.getContents();
        this.writtenTime = LocalTime.now();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalTime getWrittenTime() {
        return writtenTime;
    }

    public void setWrittenTime(LocalTime writtenTime) {
        this.writtenTime = writtenTime;
    }
}
