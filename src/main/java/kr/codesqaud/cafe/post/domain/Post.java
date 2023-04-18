package kr.codesqaud.cafe.post.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String title;
    private String writer;
    private String contents;
    private long index;
    private Timestamp wroteTime;

    public static class Builder{
        private String title;
        private String writer;
        private String contents;
        private Timestamp wroteTime;

        public Builder(){
            wroteTime = new Timestamp(System.currentTimeMillis());
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder writer(String writer){
            this.writer = writer;
            return this;
        }
        public Builder contents(String contents){
            this.contents = contents;
            return this;
        }
        public Builder wroteTime(Timestamp wroteTime){
            this.wroteTime = wroteTime;
            return this;
        }

        public Post build(){
            return new Post(this);
        }
    }

    public Timestamp getWroteTime() {
        return wroteTime;
    }

    public Post (Builder builder){
        title = builder.title;
        writer = builder.writer;
        contents = builder.contents;
        wroteTime = builder.wroteTime;
    }

    public Post setIndex(long index) {
        this.index = index;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public long getIndex() {
        return index;
    }
}
