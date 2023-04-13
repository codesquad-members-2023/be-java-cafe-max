package kr.codesqaud.cafe.domain.dto.article;

import javax.validation.constraints.NotBlank;

public class ArticleForm {
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "글을 입력해 주세요")
    private String contents;

    public ArticleForm() {
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
