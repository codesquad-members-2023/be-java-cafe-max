package kr.codesqaud.cafe.post.service;

import kr.codesqaud.cafe.post.controller.response.PostDetailResponse;
import kr.codesqaud.cafe.post.controller.response.PostListResponse;
import kr.codesqaud.cafe.post.controller.request.PostWriteRequest;
import kr.codesqaud.cafe.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void writePost(PostWriteRequest postWriteRequest) {
        postRepository.save(postWriteRequest);
    }

    public void showPostList(Model model) {
        List<PostListResponse> posts = postRepository.findAll().stream()
                .map(Post::toListResponse)
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
    }

    public void showPost(long id, Model model) {
        PostDetailResponse post = postRepository.findById(id)
                .orElseThrow().toDetailResponse();
        model.addAttribute("title", post.getTitle());
        model.addAttribute("writer", post.getWriter());
        model.addAttribute("writingTime", post.getWritingTime());
        model.addAttribute("contents", post.getContents());
    }
}
