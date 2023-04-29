package kr.codesqaud.cafe.dto.comment;

import kr.codesqaud.cafe.domain.Comment;

public class CommentUpdateDto {

    private Long id;
    private String content;

    public CommentUpdateDto(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Comment toEntity() {
        return Comment.updateOf(id, content);
    }
}
