package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {
    private String title;
    private String content;

    public ArticleDTO(String title, String content) {
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

    public Article toArticle(){
        return new Article(title,content);
    }
}
