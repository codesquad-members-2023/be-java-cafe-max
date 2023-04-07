package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryPostRepository implements PostRepository{

    private static List<Post> store = new ArrayList<>();
    private static long id = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++id);
        store.add(post);
        return post;
    }

    @Override
    public Optional<Post> findById(long id) {
        return store.stream()
                .filter(post -> post.getId() == id)
                .findAny();
    }

    @Override
    public List<Post> findAll() {
        return store;
    }
}
