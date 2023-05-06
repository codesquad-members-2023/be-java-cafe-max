package kr.codesquad.cafe.post.dto;

import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.annotation.ValidPostTextContent;
import kr.codesquad.cafe.post.annotation.ValidPostTitle;
import kr.codesquad.cafe.user.domain.User;

import java.time.LocalDateTime;

public class PostForm {
    @ValidPostTitle
    private final String title;
    @ValidPostTextContent
    private final String textContent;

    public PostForm(String title, String textContent) {
        this.title = title;
        this.textContent = textContent;
    }

    public static PostForm from(Post post) {
        return new PostForm(post.getTitle(), post.getTextContent());
    }

    public String getTitle() {
        return title;
    }


    public String getTextContent() {
        return textContent;
    }


    public Post toPost(User user) {
        return new Post.Builder()
                .title(title)
                .nickname(user.getNickname())
                .user(user)
                .textContent(textContent)
                .createdDateTime(LocalDateTime.now())
                .build();
    }
}
