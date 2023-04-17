package kr.codesqaud.cafe.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.WriterResponse;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "classpath:schema.sql")
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    void save() {
        //given
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();

        //when
        Long savedPostId = postRepository.save(basicPostData(member), member);
        PostResponse postResponse = postService.findById(savedPostId);

        //then
        assertAll(
                () -> assertEquals(basicPostData(member).getTitle(), postResponse.getTitle()),
                () -> assertEquals(basicPostData(member).getContent(), postResponse.getContent()),
                () -> assertEquals(basicPostData(member).getWriterId(), postResponse.getWriter().getWriterId()),
                () -> assertEquals(basicPostData(member).getWriteDate().withNano(0), postResponse.getWriteDate().withNano(0)),
                () -> assertEquals(basicPostData(member).getViews() + 1, postResponse.getViews()),
                () -> assertNotNull(savedPostId));
    }

    @Test
    void findById() {
        //given
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();

        Long postId = postRepository.save(basicPostData(member), member);

        //when
        PostResponse postResponse = postService.findById(postId);

        //then
        assertEquals(postId, postResponse.getPostId());
        assertEquals(basicPostData(member).getWriterId(), postResponse.getWriter().getWriterId());
    }

    @Test
    void findAll() {
        // given
        MemberJoinRequestDto requestDtoMember1 = basicMemberData();
        Long member1Id = memberService.join(requestDtoMember1);
        Member member1 = memberRepository.findById(member1Id).orElseThrow();

        MemberJoinRequestDto requestDtoMember2 = dummyMemberData();
        Long member2Id = memberService.join(requestDtoMember2);
        Member member2 = memberRepository.findById(member2Id).orElseThrow();


        Long post1Id = postRepository.save(new Post("title1", "content1", member1.getEmail(), LocalDateTime.now(), 0L), member1);
        Long post2Id = postRepository.save(new Post("title2", "content2", member2.getEmail(), LocalDateTime.now(), 0L), member2);

        // when
        List<PostResponse> result = postService.findAll();

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertFalse(result.contains(null)),
                () -> assertEquals(post1Id, result.get(1).getPostId()),
                () -> assertEquals(post2Id, result.get(0).getPostId()),
                () -> assertEquals(member1.getNickName(), result.get(1).getWriter().getNickName()),
                () -> assertEquals(member2.getNickName(), result.get(0).getWriter().getNickName())
        );
    }

    @Test
    void getWriterResponse() {
        // given
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member writer = memberRepository.findById(memberId).orElseThrow();
        Post post = basicPostData(writer);

        // when
        WriterResponse writerResponse = postService.getWriterResponse(post);

        // then
        assertEquals(writer.getNickName(), writerResponse.getNickName());
    }


    private MemberJoinRequestDto basicMemberData() {
        String email = "test@gmail.com";
        String password = "testtest";
        String nickName = "chacha";
        return new MemberJoinRequestDto(email, password, nickName);
    }

    private MemberJoinRequestDto dummyMemberData() {
        String email = "dummy@gmail.com";
        String password = "dummydummy";
        String nickName = "피오니";
        return new MemberJoinRequestDto(email, password, nickName);
    }

    private Post basicPostData(Member member) {
        String title = "피에스타";
        String content = "내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member.getEmail(), writeTime, views);
    }

}