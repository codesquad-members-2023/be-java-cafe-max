package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {

    private final Long id;
    private final String title;
    private final String content;
    private final String userId;
    private final String createTime;

    public ArticleDTO(Long id, String title, String content, String userId, String createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createTime = createTime;
    }

    public static ArticleDTO from(final Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), article.getUserId(), article.getCreateTime());
    }

    public Article toEntity(String userId) {
        return new Article(id, title, content, userId, createTime);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreateTime() {
        return createTime;
    }
}