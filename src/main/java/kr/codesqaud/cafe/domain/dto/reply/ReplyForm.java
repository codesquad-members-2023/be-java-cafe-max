package kr.codesqaud.cafe.domain.dto.reply;

public class ReplyForm {
    private String replyContent;

    private ReplyForm() {
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
