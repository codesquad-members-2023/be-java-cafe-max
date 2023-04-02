package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class PostWriteRequest {

    @NotBlank
    @Length(min = 1, max = 50)
    private final String title;

    @NotBlank
    @Length(min = 10, max = 3000)
    private final String content;

    private final Long writerId;

    private final LocalDateTime writeDate;

    public PostWriteRequest(String title, String content, Long writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writeDate = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getWriterId() {
        return writerId;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }
}
