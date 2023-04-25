package codesquad.cafe.reply.dto;

public class ReplyRequestDto {

    private String replyContents;

    public ReplyRequestDto(final String replyContents) {
        this.replyContents = replyContents;
    }

    public String getReplyContents() {
        return replyContents;
    }
}
