package kr.codesqaud.cafe.dto.post;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;

public class PostWriteRequest {
    @NotBlank
    @Length(min = 2, max = 50)
    private final String title;

    @NotBlank
    @Length(min = 2, max = 3000)
    private final String content;
    private final Long writerId;
    private final LocalDateTime writeDate;

    public PostWriteRequest(String title, String content, Long writerId, LocalDateTime writeDate) {
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

    public Post toMakePost(Member member) {
        return new Post(title, content, member, writeDate, 0L);
    }
}
