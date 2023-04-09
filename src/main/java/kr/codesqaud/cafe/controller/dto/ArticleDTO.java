package kr.codesqaud.cafe.controller.dto;

import kr.codesqaud.cafe.domain.Article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleDTO {
    @NotBlank
    @Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
    private final String title;

    @NotBlank
    @Pattern(regexp = "^[\\s\\S]{3,1000}$")
    private final String content;

    private final Integer id;

    private final String date;

    public ArticleDTO(String title, String content,Integer id,String date) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.date = date;
    }
    public Article toArticle(){
        return new Article(title,content);
    }
}
