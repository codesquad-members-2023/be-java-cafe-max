package kr.codesqaud.cafe.service;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.repository.MemoryPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final MemoryPostRepository memoryPostRepository;

    @Autowired
    public PostService(MemoryPostRepository memoryPostRepository) {
        this.memoryPostRepository = memoryPostRepository;
    }

    public Post create(Post post) {
        memoryPostRepository.save(post);
        return post;
    }

    public List<Post> findPosts() {
        return memoryPostRepository.findAll();
    }

    public Post findById(long id) {
        return memoryPostRepository.findById(id).get();
    }
}

