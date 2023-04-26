package kr.codesqaud.cafe.post.service;

import kr.codesqaud.cafe.post.controller.PostCreateRequest;
import kr.codesqaud.cafe.post.domain.Post;
import kr.codesqaud.cafe.post.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository PostRepository) {
        this.postRepository = PostRepository;
    }

    public Post create(Post post) {
        postRepository.save(post);
        return post;
    }

    public List<Post> findPosts() {
        return postRepository.findAll();
    }

    public Post findById(long id) {
        return postRepository.findByIndex(id).get();
    }

    public Post edit(PostCreateRequest postCreateRequest,long index) {
        return postRepository.edit(postCreateRequest,index);

    }
}

