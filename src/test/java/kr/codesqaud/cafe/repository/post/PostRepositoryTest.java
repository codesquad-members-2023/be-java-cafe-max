package kr.codesqaud.cafe.repository.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import kr.codesqaud.annotation.RepositoryTest;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("게시글 저장 성공")
    @Test
    void save() {
        // given
        saveMember();

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
        saveMember();
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
        saveMember();
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

    @DisplayName("게시글 수정 성공")
    @Test
    void update() {
        // given
        saveMember();
        Post post = postDummy();
        Long savedId = postRepository.save(post);
        Post updatePost = new Post(savedId, "업데이트", "업데이트 내용", post.getWriterId(),
            post.getWriteDate(), 0L);

        // when
        postRepository.update(updatePost);

        // then
        Post findPost = postRepository.findById(savedId).orElseThrow();
        assertAll(
            () -> assertEquals(updatePost.getId(), findPost.getId()),
            () -> assertEquals(updatePost.getTitle(), findPost.getTitle()),
            () -> assertEquals(updatePost.getContent(), findPost.getContent()),
            () -> assertEquals(updatePost.getWriterId(), findPost.getWriterId()),
            () -> assertEquals(updatePost.getWriteDate(), findPost.getWriteDate()),
            () -> assertEquals(updatePost.getViews(), findPost.getViews()));
    }

    private Post postDummy() {
        return new Post(null, "제목", "내용", 1L,
            LocalDateTime.now(), 0L);
    }

    private void saveMember() {
        memberRepository.save(new Member(null, "test@naver.com", "Test1234",
            "만두", LocalDateTime.now()));
        memberRepository.save(new Member(null, "test2@naver.com", "Test1234",
            "만두2", LocalDateTime.now()));
    }
}
