package kr.codesqaud.cafe.domain.dto.article;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ArticleForm {
    @NotBlank(message = "제목을 입력해 주세요")
    @Length(min = 5, max = 30, message = "제목은 5글자부터 30글자까지만 가능합니다.")
    private String title;
    @NotBlank(message = "글을 입력해 주세요")
    private String contents;

    private ArticleForm() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
