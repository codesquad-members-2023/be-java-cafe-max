package kr.codesqaud.cafe.domain;

public class Post {
    private String title;
    private String writer;
    private String contents;
    private long id;

    public static class Builder{
        private String title;
        private String writer;
        private String contents;

        public Builder(){
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
        public Post build(){
            return new Post(this);
        }
    }

    public Post (Builder builder){
        title = builder.title;
        writer = builder.writer;
        contents = builder.contents;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
