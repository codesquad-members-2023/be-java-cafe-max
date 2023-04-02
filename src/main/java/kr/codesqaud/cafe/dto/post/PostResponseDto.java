package kr.codesqaud.cafe.dto.post;

import java.time.LocalDateTime;
import java.util.UUID;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;

public class PostResponseDto {
    private final UUID postId;
    private final String title;
    private final String content;
    private final String writeId;
    private final LocalDateTime writeDate;
    private final Long views;

    public PostResponseDto(UUID postId, String title, String content, String writeId, LocalDateTime writeDate, Long views) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.writeId = writeId;
        this.writeDate = writeDate;
        this.views = views;
    }

    public UUID getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getWriteId() {
        return writeId;
    }

    public LocalDateTime getWriteDate() {
        return writeDate;
    }

    public Long getViews() {
        return views;
    }

    public static PostResponseDto of(Post post, MemberResponseDto memberResponseDto){
        return new PostResponseDto(post.getPostId(), post.getTitle(), post.getContent(), memberResponseDto.getId(),post.getWriteDate(), post.getViews());
    }
}
