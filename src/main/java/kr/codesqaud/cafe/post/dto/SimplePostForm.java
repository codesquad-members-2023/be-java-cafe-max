package kr.codesqaud.cafe.post.dto;

import kr.codesqaud.cafe.post.Post;

import java.time.LocalDateTime;

public class SimplePostForm {
    private final Long id;

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
    }

    public static SimplePostForm from(Post post) {
        return new SimplePostForm.Builder()
                .id(post.getId())
                .nickname(post.getNickname())
                .textContent(post.getTextContent())
                .title(post.getTitle())
                .createdDateTime(post.getCreatedDateTime())
                .build();
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

    public static class Builder {
        private Long id;

        private String nickname;

        private String title;

        private String textContent;

        private LocalDateTime createdDateTime;

        public Builder id(Long id) {
            this.id = id;
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
