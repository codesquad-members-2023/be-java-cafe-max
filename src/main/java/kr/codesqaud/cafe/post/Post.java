package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private User user;

    private String nickname;

    private String title;
    @Lob
    private String textContent;

    private LocalDateTime createdDateTime;

    private boolean isDeleted;


    public Post() {
    }

    private Post(Builder builder) {
        this.id = builder.id;
        this.nickname = builder.nickname;
        this.createdDateTime = builder.createdDateTime;
        this.title = builder.title;
        this.textContent = builder.textContent;
        this.user = builder.user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void disable() {
        isDeleted = true;
    }

    public static class Builder {
        private Long id;

        private String nickname;

        private String title;

        private String textContent;

        private LocalDateTime createdDateTime;
        private User user;

        public Builder() {

        }

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

        public Builder User(User user) {
            this.user = user;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
