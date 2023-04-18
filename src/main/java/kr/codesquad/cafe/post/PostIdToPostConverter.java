package kr.codesquad.cafe.post;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostIdToPostConverter implements Converter<Integer, Post> {

    private final PostService postService;

    public PostIdToPostConverter(PostService postService) {
        this.postService = postService;
    }

    @Override
    public Post convert(Integer postId) {
        return postService.findById(postId);
    }
}
