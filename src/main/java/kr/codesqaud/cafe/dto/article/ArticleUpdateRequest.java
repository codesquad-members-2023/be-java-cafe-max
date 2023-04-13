package kr.codesqaud.cafe.dto.article;

import kr.codesqaud.cafe.domain.Article;

public class ArticleUpdateRequest {
    private final Long id;
    private final String title;
    private String writer;
    private final String contents;

    public ArticleUpdateRequest(Long id, String title, String writer, String contents) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
    }

    public Article toArticle() { // DTO → Entity
        Article article = new Article();
        article.setId(this.id);
        article.setTitle(this.title);
        article.setWriter(this.writer);
        article.setContents(this.contents);
        return article;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getContents() { return contents; }
}
