package kr.codesqaud.cafe.post.dto;

import kr.codesqaud.cafe.post.Post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostForm {
    @NotEmpty
    @Size(max = 64, min = 2, message = "{error.nickname.size}")
    private String nickname;
    @NotEmpty
    @Size(max = 64, min = 2, message = "{error.title.size}")
    private String title;
    @NotEmpty
    @Size(max = 1000, min = 3, message = "{error.textContent.size}")
    private String textContent;

    public PostForm(String nickname, String title, String textContent) {
        this.nickname = nickname;
        this.title = title;
        this.textContent = textContent;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
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

    public Post toPost() {
        return new Post.Builder()
                .nickname(nickname)
                .title(title)
                .textContent(textContent)
                .createdDateTime(LocalDateTime.now())
                .build();
    }

}
