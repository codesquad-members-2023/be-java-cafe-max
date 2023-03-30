package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.dto.ArticleDto;

public class Article {
    String title;
    String content;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
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
        return new ArticleDto(title,content);
    }
}
