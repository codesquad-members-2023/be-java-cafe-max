package kr.codesqaud.cafe.controller.dto.article;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleUpdateDTO {

    @NotBlank
    @Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
    private final String title;

    @NotBlank
    @Pattern(regexp = "^[\\s\\S]{3,1000}$")
    private final String content;

    private Long idx;

    public ArticleUpdateDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setIdx(Long idx){
        this.idx = idx;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getIdx() {
        return idx;
    }
}
