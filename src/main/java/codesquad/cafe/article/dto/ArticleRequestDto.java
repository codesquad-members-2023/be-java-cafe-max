package codesquad.cafe.article.dto;

import codesquad.cafe.article.domain.Article;

import javax.validation.constraints.NotBlank;


public class ArticleRequestDto {

    @NotBlank(message = "제목은 반드시 작성해야합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 작성해야 합니다.")
    private String contents;

    public ArticleRequestDto(final String title, final String contents) {
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article(title, contents);
    }
}
