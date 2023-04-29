package kr.codesqaud.cafe.dto.comment;

import kr.codesqaud.cafe.domain.Comment;

public class CommentWriteDto {

    private Long memberId;
    private String memberEmail;
    private Long commentId;
    private Long postId;
    private String content;

    public CommentWriteDto(Long memberId, String memberEmail, Long commentId, Long postId, String content) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.commentId = commentId;
        this.postId = postId;
        this.content = content;
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void initMemberInfo(Long memberId,String memberEmail) {
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public Comment toEntity() {
        return new Comment(postId, memberId, memberEmail, content);
    }
}
