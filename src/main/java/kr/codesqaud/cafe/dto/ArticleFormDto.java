package kr.codesqaud.cafe.dto;

import javax.validation.constraints.NotBlank;

public class ArticleFormDto {

    @NotBlank(message = "공백 입력 불가")
    private String title;
    @NotBlank(message = "공백 입력 불가")
    private String contents;


    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
