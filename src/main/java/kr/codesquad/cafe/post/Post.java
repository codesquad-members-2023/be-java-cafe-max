package kr.codesquad.cafe.post;

import kr.codesquad.cafe.comment.Comment;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "post")
    protected List<Comment> comments;

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

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void disable() {
        isDeleted = true;
    }

    public void setFrom(PostForm postForm) {
        title = postForm.getTitle();
        textContent = postForm.getTextContent();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public boolean canDelete() {
        return getComments().stream()
                .filter(comment -> !comment.isSameId(user.getId()))
                .allMatch(Comment::isDeleted);
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
