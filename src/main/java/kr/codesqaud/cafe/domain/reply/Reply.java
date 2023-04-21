package kr.codesqaud.cafe.domain.reply;

public class Reply {

    private int index;
    private int articleIdx;
    private String replyWriter;
    private String replyContents;
    private String date;

    public static class Builder {
        private int index;
        private int articleIdx;
        private String replyWriter;
        private String replyContents;

        private String date;

        public Builder index(int val) {
            index = val;
            return this;
        }

        public Builder articleIdx(int val) {
            articleIdx = val;
            return this;
        }

        public Builder replyWriter(String val) {
            replyWriter = val;
            return this;
        }

        public Builder replyContents(String val) {
            replyContents = val;
            return this;
        }

        public Builder date(String val) {
            date = val;
            return this;
        }

        public Reply build() {
            return new Reply(this);
        }
    }

    private Reply(Builder builder) {
        index = builder.index;
        articleIdx = builder.articleIdx;
        replyWriter = builder.replyWriter;
        replyContents = builder.replyContents;
        date = builder.date;
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

    public boolean validateAuthor(String nickName) {
        if (replyWriter.equals(nickName)) {
            return true;
        }
        return false;
    }
}
