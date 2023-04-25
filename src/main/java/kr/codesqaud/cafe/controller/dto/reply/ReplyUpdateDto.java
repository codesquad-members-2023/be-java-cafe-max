package kr.codesqaud.cafe.controller.dto.reply;

import kr.codesqaud.cafe.domain.Reply;

public class ReplyUpdateDto {
    private Long id;
    private String comments;

    public ReplyUpdateDto() {
    }

    public ReplyUpdateDto(Long id, String comments) {
        this.id = id;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public String getComments() {
        return comments;
    }

    public Reply toReply(Long userId) {
        return Reply.updateOf(id, userId, comments);
    }
}
