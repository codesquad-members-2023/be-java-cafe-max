package kr.codesqaud.cafe.controller.dto.request;

public class ReplyRequest {
    private final String comment;

    public ReplyRequest(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }
}
