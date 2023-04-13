package kr.codesqaud.cafe.dto.post;

import javax.validation.constraints.NotBlank;
import kr.codesqaud.cafe.domain.Post;
import org.hibernate.validator.constraints.Length;

public class PostModifyRequest {

    private Long id;

    @NotBlank
    @Length(min = 2, max = 50)
    private final String title;

    @NotBlank
    @Length(min = 2, max = 3000)
    private final String content;

    public PostModifyRequest(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Post toPost() {
        return new Post(id, title, content, null, null, null);
    }

    public static PostModifyRequest from(PostResponse postResponse) {
        return new PostModifyRequest(postResponse.getId(), postResponse.getTitle(),
            postResponse.getContent());
    }
}
