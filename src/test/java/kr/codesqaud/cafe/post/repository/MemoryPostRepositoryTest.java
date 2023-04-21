package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemoryPostRepositoryTest {

    private PostRepository postRepository;

    @BeforeEach
    void init() {
        postRepository = new MemoryPostRepository();
    }

    @Test
    @DisplayName("객체 정상 저장 여부 확인")
    void save() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime, false));

        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertEquals(1, posts.size());
        assertEquals(posts.get(0), new Post(1L, "writer", "name", "title", "contents", dateTime, false));
    }

    @Test
    @DisplayName("객체 순차 저장 확인")
    void save_many() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime, false));
        postRepository.save(new Post(null, "writer", "name", "title2", "contents", dateTime.plusHours(1), false));

        // when
        List<Post> posts = postRepository.findAll();
        Optional<Post> post1 = postRepository.findById(1);
        Optional<Post> post2 = postRepository.findById(2);

        // then
        assertEquals(2, posts.size());
        assertTrue(post1.isPresent());
        assertTrue(post2.isPresent());
        assertEquals(post1.get(), new Post(1L, "writer", "name", "title", "contents", dateTime, false));
        assertEquals(post2.get(), new Post(2L, "writer", "name", "title2", "contents", dateTime.plusHours(1), false));
    }

    @Test
    @DisplayName("없는 게시글 가져올 때")
    void findById() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime, false));

        // when
        Optional<Post> post = postRepository.findById(2);

        // then
        assertTrue(post.isEmpty());
    }

    @Test
    @DisplayName("게시글 최신 순 정렬 확인")
    void findAll() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime, false));
        postRepository.save(new Post(null, "writer", "name", "title2", "contents", dateTime.plusHours(1), false));

        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertEquals(2, posts.size());
        assertEquals(posts.get(0), new Post(2L, "writer", "name", "title2", "contents", dateTime.plusHours(1), false));
        assertEquals(posts.get(1), new Post(1L, "writer", "name", "title", "contents", dateTime, false));
    }
}