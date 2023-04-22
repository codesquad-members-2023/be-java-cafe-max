package kr.codesqaud.cafe.dto.comment;

public class CommentDeleteResponse {

    private Boolean isValid;

    public CommentDeleteResponse(Boolean isValid) {
        this.isValid = isValid;
    }

    public Boolean getValid() {
        return isValid;
    }
}
