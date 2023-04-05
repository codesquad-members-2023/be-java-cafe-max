package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.ArticleForm;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;

    public Article() {
    }

    public Article(ArticleForm form) {
        this.writer = form.getWriter();
        this.title = form.getTitle();
        this.contents = form.getContents();
    }

    public String getWriter() {
        return writer;
    }

    public Long getId() {
        return id;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
