package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import org.hibernate.validator.constraints.Length;

public class PostWriteRequest {

    @NotBlank
    @Length(min = 2, max = 50)
    private final String title;

    @NotBlank
    @Length(min = 2, max = 3000)
    private final String content;

    private Long writerId;

    private final LocalDateTime writeDateTime;

    public PostWriteRequest(String title, String content, Long writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.writeDateTime = LocalDateTime.now();
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

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public Post toPost(Member member) {
        return new Post(title, content, member, writeDateTime);
    }
}
