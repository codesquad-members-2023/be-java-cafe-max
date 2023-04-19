package kr.codesqaud.cafe.reply;

public class ReplyResponseDto {

    private String userName;
    private String contents;
    private String createDateTime;

    public ReplyResponseDto(String userName, String contents, String createDateTime) {
        this.userName = userName;
        this.contents = contents;
        this.createDateTime = createDateTime;
    }

    public String getUserName() {
        return userName;
    }

    public String getContents() {
        return contents;
    }

    public String getCreateDateTime() {
        return createDateTime;
    }
}
