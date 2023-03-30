package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.ArticleDto;

public class Article {
    private String title;
    private String content;
    private int id;

    public Article(String title, String content, int id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArticleDto toDto(){
        return new ArticleDto(title,content,id);
    }
}
