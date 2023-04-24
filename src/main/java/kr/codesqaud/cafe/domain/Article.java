package kr.codesqaud.cafe.domain;

import kr.codesqaud.cafe.controller.dto.article.ArticleDTO;

public class Article {

    private Long id;
    private String title;
    private String content;
    private String userName;
    private String createTime;

    public Article() {
    }

    public Article(Long id, String title, String content, String userName, String createTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.createTime = createTime;
    }

    public void create(Long id, Article article) {
        this.id = id;
        this.title = article.getTitle();
        this.content = article.getContent();
        this.userName = article.userName;
        this.createTime = article.getCreateTime();
    }

    public void update(ArticleDTO articleDTO) {
        this.title = articleDTO.getTitle();
        this.content = articleDTO.getContent();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}