package kr.codesqaud.cafe.board.dto;

public class CommentWriteForm {
    private Long postId;
    private String contents;

    public CommentWriteForm(Long postId, String contents) {
        this.postId = postId;
        this.contents = contents;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContents() {
        return contents;
    }

}
