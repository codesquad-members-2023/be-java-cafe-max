package kr.codesqaud.cafe.repository.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import kr.codesqaud.cafe.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemoryPostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글 저장 성공")
    @Test
    void save() {
        // given

        // when
        Long savedId = postRepository.save(postDummy());

        // then
        Optional<Post> findPost = postRepository.findById(savedId);
        assertAll(
            () -> assertTrue(findPost.isPresent()),
            () -> assertEquals(savedId, findPost.get().getId()));
    }

    @DisplayName("게시글 단건 조회 성공")
    @Test
    void findById() {
        // given
        Post post = postDummy();
        Long savedId = postRepository.save(post);

        // when
        Post findPost = postRepository.findById(savedId).orElseThrow();

        // then
        assertAll(
            () -> assertEquals(savedId, findPost.getId()),
            () -> assertEquals(post.getTitle(), findPost.getTitle()),
            () -> assertEquals(post.getContent(), findPost.getContent()),
            () -> assertEquals(post.getWriterId(), findPost.getWriterId()),
            () -> assertEquals(post.getWriteDate(), findPost.getWriteDate()),
            () -> assertEquals(post.getViews(), findPost.getViews()));
    }

    @DisplayName("게시글 전체 조회 성공")
    @Test
    void findAll() {
        // given
        int postCount = 5;
        IntStream.rangeClosed(1, postCount)
            .forEach(index -> {
                    String title = String.format("제목%d", index);
                    String content = String.format("내용%d", index);
                    postRepository.save(new Post(null, title, content, 1L,
                        LocalDateTime.now(), (long) index));
            });

        // when
        List<Post> findAll = postRepository.findAll();

        // then
        assertEquals(postCount, findAll.size());
    }

    private Post postDummy() {
        return new Post(null, "제목", "내용", 1L,
            LocalDateTime.now(), 0L);
    }

    private Post postDummy2() {
        return new Post(null, "제목테스트", "내용테스트", 2L,
            LocalDateTime.now(), 5L);
    }
}
