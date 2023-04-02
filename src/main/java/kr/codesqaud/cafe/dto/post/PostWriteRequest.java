package kr.codesqaud.cafe.dto.post;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import kr.codesqaud.cafe.domain.Post;

public class PostWriteRequest {
    @NotBlank
    @Length(min = 1,max = 50)
    private final String title;

    @NotBlank
    @Length(min = 10)
    private final String content;
    private final String writerId;
    private final LocalDateTime localDateTime;

    public PostWriteRequest(String title, String content, String writerId, LocalDateTime localDateTime) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.localDateTime = localDateTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriterId() {
        return writerId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Post toEntity(UUID postId){
        return new Post(postId,title,content,writerId,LocalDateTime.now(),0L);
    }
}
