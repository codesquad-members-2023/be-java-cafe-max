package kr.codesqaud.cafe.dto.post;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class PostEditRequest {
    private Long id;

    @Length(min = 2, max = 50)
    private final String title;

    @NotBlank
    @Length(min = 2, max = 3000)
    private final String content;

    public PostEditRequest(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Long getid() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
