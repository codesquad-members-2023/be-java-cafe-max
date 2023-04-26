package kr.codesqaud.cafe.dto.comment;

public class CommentDeleteResponse {

    private final Boolean isDeleted;

    public CommentDeleteResponse(Boolean isValid) {
        this.isDeleted = isValid;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }
}
