package kr.codesqaud.cafe.app.comment.controller.dto;

import kr.codesqaud.cafe.app.comment.entity.Comment;

public class CommentModifyRequest {

    private final Long id;
    private final String content;

    public CommentModifyRequest(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Comment toEntity() {
        return Comment.builder().id(id).content(content).build();
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
