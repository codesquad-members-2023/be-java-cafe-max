package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MemoryPostRepository implements PostRepository {

    private static List<Post> store = new ArrayList<>();
    private static long index = 0L;

    @Override
    public Post save(Post post) {
        post.setIndex(++index);
        store.add(post);
        return post;
    }

    @Override
    public Optional<Post> findByIndex(long index) {
        return store.stream()
                .filter(post -> post.getIndex() == index)
                .findAny();
    }

    @Override
    public List<Post> findAll() {
        return store;
    }
}
