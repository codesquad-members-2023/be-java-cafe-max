package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.article.ArticleForm;

import java.time.LocalDateTime;

public class Article {
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime currentTime;

    public Article() {
    }

    // TODO: DTO를 domain에 생성자로 넣어주는 것은 좋지 않기 때문에 수정 예정
    public Article(ArticleForm form) {
        this.writer = form.getWriter();
        this.title = form.getTitle();
        this.contents = form.getContents();
        this.currentTime = LocalDateTime.now();
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
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
