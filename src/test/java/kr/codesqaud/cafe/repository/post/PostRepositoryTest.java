package kr.codesqaud.cafe.repository.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;
import kr.codesqaud.annotation.RepositoryTest;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("게시글 저장할 때 제목, 내용, 작성자 아이디, 게시글 작성 날짜, 조회수를 입력하면 게시글 저장된다")
    @Test
    void save() {
        // given
        Long savedMemberId = saveMember();

        // when
        Long savedId = postRepository.save(postDummy(savedMemberId));

        // then
        Optional<Post> findPost = postRepository.findById(savedId);
        assertAll(
            () -> assertTrue(findPost.isPresent()),
            () -> assertEquals(savedId, findPost.get().getId()));
    }

    @DisplayName("게시글 아이디로 조회할 때 게시글이 있다면 게시글을 반환한다")
    @Test
    void findById() {
        // given
        Long savedMemberId = saveMember();
        Post post = postDummy(savedMemberId);
        Long savedId = postRepository.save(post);

        // when
        Post findPost = postRepository.findById(savedId).orElseThrow();

        // then
        assertAll(
            () -> assertEquals(savedId, findPost.getId()),
            () -> assertEquals(post.getTitle(), findPost.getTitle()),
            () -> assertEquals(post.getContent(), findPost.getContent()),
            () -> assertEquals(post.getWriter().getId(), findPost.getWriter().getId()),
            () -> assertEquals(post.getWriteDateTime(), findPost.getWriteDateTime()),
            () -> assertEquals(post.getViews(), findPost.getViews()));
    }

    @DisplayName("게시글 전체 조회할 때 게시글이 있다면 모든 게시글을 반환한다")
    @Test
    void findAll() {
        // given
        Long savedMemberId = saveMember();
        int postCount = 5;
        IntStream.rangeClosed(1, postCount)
            .forEach(index -> {
                    String title = String.format("제목%d", index);
                    String content = String.format("내용%d", index);
                    postRepository.save(new Post(title, content, new Member(savedMemberId), LocalDateTime.now()));
            });

        // when
        List<Post> findAll = postRepository.findAll();

        // then
        assertEquals(postCount, findAll.size());
    }

    @DisplayName("게시글 수정할 때 아이디, 제목, 내용을 입력하면 게시글이 수정된다")
    @Test
    void update() {
        // given
        Long savedMemberId = saveMember();
        Post post = postDummy(savedMemberId);
        Long savedId = postRepository.save(post);
        Post updatePost = new Post(savedId, "업데이트", "업데이트 내용");

        // when
        postRepository.update(updatePost);

        // then
        Post findPost = postRepository.findById(savedId).orElseThrow();
        assertAll(
            () -> assertEquals("업데이트", findPost.getTitle()),
            () -> assertEquals("업데이트 내용", findPost.getContent()));
    }

    @DisplayName("게시글 아이디로 삭제할 떄 게시글이 있다면 게시글을 삭제한다")
    @Test
    void delete() {
        // given
        Long savedMemberId = saveMember();
        Long savedId = postRepository.save(postDummy(savedMemberId));

        // when
        postRepository.delete(savedId);

        // then
        assertThrows(NoSuchElementException.class,
            () -> postRepository.findById(savedId).orElseThrow());
    }

    private Post postDummy(Long writerId) {
        return new Post("제목", "내용", new Member(writerId), LocalDateTime.now());
    }

    private Long saveMember() {
        return memberRepository.save(new Member("test@naver.com", "Test1234", "만두"));
    }
}
