package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {

    private final Long id;
    private final String title;
    private final String content;

    public ArticleDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static ArticleDTO toArticleDTO(Article article) {
        return new ArticleDTO(article.getId(), article.getTitle(), article.getContent());
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
}
