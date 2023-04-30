package kr.codesqaud.cafe.dto.comment;

public class CommentDeleteResponse {

    private final Boolean isDeleted;

    public CommentDeleteResponse(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }
}
