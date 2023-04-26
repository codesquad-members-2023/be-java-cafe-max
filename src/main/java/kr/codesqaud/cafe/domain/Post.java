package kr.codesqaud.cafe.domain;

import java.time.LocalDateTime;

public class Post {

    private final Long id;
    private final String title;
    private final String content;
    private final Member writer;
    private final LocalDateTime writeDateTime;
    private final Long views;

    private Post(Long id, String title, String content, Member writer, LocalDateTime writeDateTime,
        Long views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.writeDateTime = writeDateTime;
        this.views = views;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Member getWriter() {
        return writer;
    }

    public LocalDateTime getWriteDateTime() {
        return writeDateTime;
    }

    public Long getViews() {
        return views;
    }

    public boolean equalsWriterId(Long writerId) {
        return writer.equalsId(writerId);
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {

        private Long id;
        private String title;
        private String content;
        private Member writer;
        private LocalDateTime writeDate;
        private Long views;

        public PostBuilder() {
        }

        public PostBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostBuilder writer(Member writer) {
            this.writer = writer;
            return this;
        }

        public PostBuilder writeDate(LocalDateTime writeDate) {
            this.writeDate = writeDate;
            return this;
        }

        public PostBuilder views(Long views) {
            this.views = views;
            return this;
        }

        public Post build() {
            return new Post(id, title, content, writer, writeDate, views);
        }
    }
}
