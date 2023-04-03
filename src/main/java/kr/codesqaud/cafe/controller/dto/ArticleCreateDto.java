package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleCreateDto {
    private String title;
    private String userId;
    private String contents;

    public ArticleCreateDto(String title, String userId, String contents) {
        this.title = title;
        this.userId = userId;
        this.contents = contents;
    }

    public ArticleCreateDto() {
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Article toArticle() {
        return new Article(title, userId, contents);
    }
}
