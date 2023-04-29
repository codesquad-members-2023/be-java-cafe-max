package kr.codesqaud.cafe.dto.comment;

import java.util.List;

public class CommentsResponse {

    private List<CommentResponse> commentResponses;
    private Boolean isNext;

    public CommentsResponse() {
    }

    public CommentsResponse(List<CommentResponse> commentResponses, Boolean isNext) {
        this.commentResponses = commentResponses;
        this.isNext = isNext;
    }

    public List<CommentResponse> getCommentResponses() {
        return commentResponses;
    }

    public Boolean isNext() {
        return isNext;
    }
}
