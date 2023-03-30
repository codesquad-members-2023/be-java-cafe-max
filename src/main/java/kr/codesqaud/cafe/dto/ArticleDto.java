package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDto {
    private String title;
    private String content;
    private Integer id;

    public ArticleDto(String title, String content,Integer id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Article toArticle(){
        return new Article(title,content,id);
    }
}
