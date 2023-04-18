package kr.codesquad.cafe.comment;

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
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;

    private String comment;

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

    public String getComment() {
        return comment;
    }

    public boolean isDeleted() {
        return isDeleted;
    }


    public Comment(Builder builder) {
        this.post = builder.post;
        this.user = builder.user;
        this.comment = builder.comment;
    }

    public static class Builder {
        private Post post;
        private User user;
        private String comment;

        public Builder post(Post post) {
            this.post = post;
            return this;
        }


        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

}
