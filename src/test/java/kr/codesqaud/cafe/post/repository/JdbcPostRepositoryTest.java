package kr.codesqaud.cafe.post.repository;

import kr.codesqaud.cafe.post.service.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
public class JdbcPostRepositoryTest {

    private PostRepository postRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void init() {
        postRepository = new JdbcPostRepository(dataSource);
    }

    @Test
    @DisplayName("객체 순차 저장 확인")
    void save_many() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        Post post1 = postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime, false));
        Post post2 = postRepository.save(new Post(null, "writer", "name", "title2", "contents", dateTime.plusHours(1), false));

        // when
        List<Post> posts = postRepository.findAll();
        long id1 = post1.getId();
        long id2 = post2.getId();

        // then
        assertEquals(2, posts.size());
        assertEquals(1, id2 - id1);
        assertEquals(new Post(id1, "writer", "name", "title", "contents", dateTime, false), post1);
        assertEquals(new Post(id2, "writer", "name", "title2", "contents", dateTime.plusHours(1), false), post2);
    }

    @Test
    @DisplayName("없는 게시글 가져올 때")
    void findById() {
        // given

        // when
        Optional<Post> post = postRepository.findById(3);

        // then
        assertTrue(post.isEmpty());
    }

    @Test
    @DisplayName("게시글 최신 순 정렬 확인")
    void findAll() {
        // given
        LocalDateTime dateTime = LocalDateTime.now();
        postRepository.save(new Post(null, "writer", "name", "title", "contents", dateTime,false));
        postRepository.save(new Post(null, "writer", "name", "title2", "contents", dateTime.plusHours(1),false));

        // when
        List<Post> posts = postRepository.findAll();

        // then
        assertEquals(2, posts.size());
        assertEquals("title2", posts.get(0).getTitle());
        assertEquals("title", posts.get(1).getTitle());
    }
}
