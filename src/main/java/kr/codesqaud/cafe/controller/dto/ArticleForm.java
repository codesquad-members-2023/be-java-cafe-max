package kr.codesqaud.cafe.controller.dto;

import javax.validation.constraints.NotBlank;

public class ArticleForm {
    @NotBlank(message = "이름을 입력해 주세요")
    private String writer;
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    @NotBlank(message = "글을 입력해 주세요")
    private String contents;

    public ArticleForm() {
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
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
