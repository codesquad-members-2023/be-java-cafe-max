package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleDTO {
    @NotBlank
    @Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
    private String title;

    @NotBlank
    @Pattern(regexp = "^[\\s\\S]{3,1000}$")
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
