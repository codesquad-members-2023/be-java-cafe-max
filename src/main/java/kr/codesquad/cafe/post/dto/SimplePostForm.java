package kr.codesquad.cafe.post.dto;

import kr.codesquad.cafe.post.Post;

import java.time.LocalDateTime;

public class SimplePostForm {
    public static final int MAX_LENGTH = 150;
    public static final int BEGIN_INDEX = 0;
    private final long id;

    private final long commentCount;

    private final String nickname;

    private final String title;

    private final String textContent;

    private final LocalDateTime createdDateTime;

    private SimplePostForm(Builder builder) {
        id = builder.id;
        nickname = builder.nickname;
        title = builder.title;
        textContent = builder.textContent;
        createdDateTime = builder.createdDateTime;
        commentCount = builder.commentCount;
    }

    public static SimplePostForm from(Post post) {
        return new Builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .textContent(getSimpleTextContent(post))
                .title(post.getTitle())
                .commentCount(post.getComments().size())
                .createdDateTime(post.getCreatedDateTime())
                .build();
    }

    private static String getSimpleTextContent(Post post) {
        return (post.getTextContent().length() > MAX_LENGTH) ? post.getTextContent().substring(BEGIN_INDEX, MAX_LENGTH) : post.getTextContent();
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTitle() {
        return title;
    }

    public String getTextContent() {
        return textContent;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public static class Builder {
        private long id;

        private long commentCount;

        private String nickname;

        private String title;

        private String textContent;

        private LocalDateTime createdDateTime;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder commentCount(long commentCount) {
            this.commentCount = commentCount;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder textContent(String textContent) {
            this.textContent = textContent;
            return this;
        }

        public Builder createdDateTime(LocalDateTime createdDateTime) {
            this.createdDateTime = createdDateTime;
            return this;
        }

        public SimplePostForm build() {
            return new SimplePostForm(this);
        }
    }

}
