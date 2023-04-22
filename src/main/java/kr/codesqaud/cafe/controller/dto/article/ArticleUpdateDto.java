package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleUpdateDto {
    private Long id;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 2, max = 30, message = "제목은 {min} ~ {max} 길이로 입력하세요.")
    private String title;

    @Nullable
    @Size(max = 1000, message = "본문은 {max}글자 이하로 입력하세요.")
    private String contents;

    public ArticleUpdateDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public ArticleUpdateDto() {
    }

    public ArticleUpdateDto(ArticleReadDto articleReadDto) {
        this(articleReadDto.getId(), articleReadDto.getTitle(), articleReadDto.getContents());
    }

    public Article toArticle() {
        return new Article(id, title, contents);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(@Nullable String contents) {
        this.contents = contents;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
