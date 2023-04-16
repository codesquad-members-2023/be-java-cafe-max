package kr.codesqaud.cafe.controller.dto.article;

import kr.codesqaud.cafe.domain.Article;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ArticleCreateDto {
    private static final String BLANK = "";

    private Long userFk;

    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 2, max = 30, message = "제목은 {min} ~ {max} 길이로 입력하세요.")
    private String title;

    @Nullable
    @Size(max = 1000, message = "본문은 {max}글자 이하로 입력하세요.")
    private String contents;

    public ArticleCreateDto() {
    }

    public Long getUserFk() {
        return userFk;
    }

    public void setUserFk(Long userFk) {
        this.userFk = userFk;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(@Nullable String contents) {
        this.contents = contents;
    }

    public Article toArticle() {
        if (isEmptyContents()) {
            contents = BLANK;
        }

        return new Article(userFk, title, contents);
    }

    private boolean isEmptyContents() {
        return !StringUtils.hasText(contents);
    }
}
