package kr.codesqaud.cafe.comment;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.Post;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    private Long id;
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;

    private String content;

    private boolean isDeleted;

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
}
