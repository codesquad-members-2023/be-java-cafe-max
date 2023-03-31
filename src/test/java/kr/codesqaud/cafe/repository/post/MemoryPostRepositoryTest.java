package kr.codesqaud.cafe.repository.post;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import kr.codesqaud.cafe.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryPostRepositoryTest {

    private PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        postRepository = new MemoryPostRepository();
    }

    @DisplayName("게시글 저장 성공")
    @Test
    void save() {
        // given

        // when
        Long savedId1 = postRepository.save(postDummy());
        Long savedId2 = postRepository.save(postDummy2());

        // then
        assertAll(
            () -> assertEquals(1, savedId1),
            () -> assertEquals(2, savedId2));
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
            () -> assertEquals(1, savedId),
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
                    postRepository.save(new Post(null, title, content, UUID.randomUUID().toString(),
                        LocalDateTime.now(), (long) index));
            });

        // when
        List<Post> findAll = postRepository.findAll();

        // then
        assertEquals(postCount, findAll.size());
    }

    private Post postDummy() {
        return new Post(null, "제목", "내용", UUID.randomUUID().toString(),
            LocalDateTime.now(), 0L);
    }

    private Post postDummy2() {
        return new Post(null, "제목테스트", "내용테스트", UUID.randomUUID().toString(),
            LocalDateTime.now(), 5L);
    }
}
