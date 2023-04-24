package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {

    private final Long id;
    private final String title;
    private final String content;
    private final String userName;
    private final String createTime;

    public ArticleDTO(Long id, String title, String content, String userName, String createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createTime = createTime;
    }

    public static ArticleDTO from(final Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent(), article.getUserName(), article.getCreateTime());
    }

    public Article toEntity(String name) {
        return new Article(id, title, content, name, createTime);
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

    public String getUserName() {
        return userName;
    }

    public String getCreateTime() {
        return createTime;
    }
}