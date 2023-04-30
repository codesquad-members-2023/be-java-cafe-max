package kr.codesqaud.cafe.dto;

import kr.codesqaud.cafe.domain.reply.Reply;

public class ReplyResponseDto {

    private int index;
    private int articleIdx;
    private String replyWriter;
    private String replyContents;
    private String date;


    public ReplyResponseDto(Reply reply) {
        this.index = reply.getIndex();
        this.articleIdx = reply.getArticleIdx();
        this.replyWriter = reply.getReplyWriter();
        this.replyContents = reply.getReplyContents();
        this.date = reply.getDate();
    }

    public int getIndex() {
        return index;
    }

    public int getArticleIdx() {
        return articleIdx;
    }

    public String getReplyWriter() {
        return replyWriter;
    }

    public String getReplyContents() {
        return replyContents;
    }

    public String getDate() {
        return date;
    }
}
