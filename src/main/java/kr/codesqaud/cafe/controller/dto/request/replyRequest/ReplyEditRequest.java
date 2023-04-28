package kr.codesqaud.cafe.controller.dto.request.replyRequest;

public class ReplyEditRequest {
    private final String newComment;

    public ReplyEditRequest(String newComment) {
        this.newComment = newComment;
    }

    public String getNewComment() {
        return newComment;
    }
}
