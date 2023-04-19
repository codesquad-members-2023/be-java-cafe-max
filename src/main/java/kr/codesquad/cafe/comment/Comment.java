package kr.codesquad.cafe.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id @GeneratedValue
    public Long id;
    @JsonIgnore
    @ManyToOne
    private Post post;
    @JsonIgnore
    @ManyToOne
    private User user;

    private String content;

    private boolean isDeleted;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }


    public Comment(Builder builder) {
        this.post = builder.post;
        this.user = builder.user;
        this.content = builder.content;
    }

    public boolean isSameId(Long id) {
        return user.isSameId(id);
    }

    public static class Builder {
        private Post post;
        private User user;
        private String content;

        public Builder post(Post post) {
            this.post = post;
            return this;
        }


        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

}
