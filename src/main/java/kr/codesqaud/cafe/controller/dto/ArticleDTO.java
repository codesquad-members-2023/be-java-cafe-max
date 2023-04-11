package kr.codesqaud.cafe.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleDTO {
    @NotBlank
    @Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
    private final String title;

    @NotBlank
    @Pattern(regexp = "^[\\s\\S]{3,1000}$")
    private final String content;

    private final Long id;

    private final String date;

    public ArticleDTO(String title, String content, Long id, String date) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
