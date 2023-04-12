package kr.codesqaud.cafe.post.dto;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.post.Post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostForm {
    @NotEmpty
    @Size(max = 64, min = 2, message = "{error.title.size}")
    private String title;
    @NotEmpty
    @Size(max = 1000, min = 3, message = "{error.textContent.size}")
    private String textContent;

    public PostForm(String title, String textContent) {
        this.title = title;
        this.textContent = textContent;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


    public String getTitle() {
        return title;
    }

    public String getTextContent() {
        return textContent;
    }

    public static PostForm from(Post post) {
        return new PostForm(post.getTitle(), post.getTextContent());
    }

    public Post toPost(User user) {
        return new Post.Builder()
                .title(title)
                .nickname(user.getNickname())
                .User(user)
                .textContent(textContent)
                .createdDateTime(LocalDateTime.now())
                .build();
    }

    public void editPost(Post post) {
        post.setTitle(this.title);
        post.setTextContent(this.textContent);
    }
}
