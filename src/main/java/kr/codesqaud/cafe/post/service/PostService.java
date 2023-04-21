package kr.codesqaud.cafe.post.service;

import kr.codesqaud.cafe.post.controller.response.PostDetailResponse;
import kr.codesqaud.cafe.post.controller.response.PostListResponse;
import kr.codesqaud.cafe.post.controller.response.SimplePostResponse;
import kr.codesqaud.cafe.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void writePost(Post post) {
        postRepository.save(post);
    }

    public boolean updatePost(long id, Post post) {
        boolean isWriterMatch = postRepository.findById(id).orElseThrow().getWriterId().equals(post.getWriterId());
        if (isWriterMatch) {
            postRepository.update(id, post);
        }
        return isWriterMatch;
    }

    public List<PostListResponse> getPostList() {
        return postRepository.findAll().stream()
                .map(PostListResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public PostDetailResponse getPostById(long id) {
        return PostDetailResponse.from(postRepository.findById(id).orElseThrow());
    }

    public SimplePostResponse getSimplePostById(long id) {
        return SimplePostResponse.from(postRepository.findById(id).orElseThrow());
    }
}
