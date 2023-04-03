package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleCreateDto {
    private final String title;
    private final String userId;
    private final String content;


    public ArticleCreateDto(String title, String userId, String content) {
        this.title = title;
        this.userId = userId;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public Article toArticle() {
        return new Article(title, userId, content);
    }
}
