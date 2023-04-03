package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

public class ArticleDTO {
    private String title;
    private String content;
    private Integer id;

    public ArticleDTO(String title, String content,Integer id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }
    public Article toArticle(){
        return new Article(title,content);
    }
}
