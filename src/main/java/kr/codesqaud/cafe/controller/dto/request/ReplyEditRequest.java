package kr.codesqaud.cafe.controller.dto.request;

public class ReplyEditRequest {
    private final String originalComment;
    private final String newComment;

    public ReplyEditRequest(String originalComment, String newComment) {
        this.originalComment = originalComment;
        this.newComment = newComment;
    }

    public String getOriginalComment() {
        return originalComment;
    }

    public String getNewComment() {
        return newComment;
    }
}
