package codesquad.cafe.domain.article.dto;

import codesquad.cafe.domain.article.domain.Article;

import javax.validation.constraints.NotBlank;


public class ArticleRequestDto {

    @NotBlank(message = "작성자 이름은 반드시 작성해야합니다.")
    private String writer;

    @NotBlank(message = "제목은 반드시 작성해야합니다.")
    private String title;

    @NotBlank(message = "내용은 반드시 작성해야 합니다.")
    private String contents;

    public ArticleRequestDto(final String writer, final String title, final String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Article toEntity() {
        return new Article( writer, title, contents);
    }
}
