package kr.codesqaud.cafe.post.dto;

import kr.codesqaud.cafe.post.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostForm {
    @NotEmpty
    @NotBlank
    @Size(max = 64, min = 2, message = "{error.nickname.size}")
    private final String nickname;
    @NotEmpty
    @NotBlank
    @Size(max = 64, min = 2, message = "{error.title.size}")
    private final String title;
    @NotEmpty
    @Size(max = 1000, min = 3, message = "{error.textContent.size}")
    private final String textContent;

    public PostForm(String nickname, String title, String textContent) {
        this.nickname = nickname;
        this.title = title;
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
