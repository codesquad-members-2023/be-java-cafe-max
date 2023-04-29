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
import kr.codesqaud.cafe.session.LoginMemberSession;

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
        Long savedId = postRepository.save(basicPostData(member), member);
        PostResponse postResponse = postService.findById(savedId);

        //then
        assertAll(
                () -> assertEquals("피에스타", postResponse.getTitle()),
                () -> assertEquals("내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게", postResponse.getContent()),
                () -> assertEquals("test@gmail.com", postResponse.getWriter().getWriterEmail()),
                () -> assertEquals(basicPostData(member).getWriteDate().withNano(0), postResponse.getWriteDate().withNano(0)),
                () -> assertEquals(1L, postResponse.getViews()),
                () -> assertNotNull(savedId));
    }

    @Test
    void findById() {
        //given
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();

        Long id = postRepository.save(basicPostData(member), member);

        //when
        PostResponse postResponse = postService.findById(id);

        //then
        assertEquals(id, postResponse.getId());
        assertEquals("test@gmail.com", postResponse.getWriter().getWriterEmail());
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
                () -> assertEquals(post1Id, result.get(1).getId()),
                () -> assertEquals(post2Id, result.get(0).getId()),
                () -> assertEquals("차차", result.get(1).getWriter().getNickname()),
                () -> assertEquals("피오니", result.get(0).getWriter().getNickname())
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
        assertEquals(writer.getNickname(), writerResponse.getNickname());
    }


    private MemberJoinRequestDto basicMemberData() {
        String email = "test@gmail.com";
        String password = "testtest";
        String nickname = "차차";
        return new MemberJoinRequestDto(email, password, nickname);
    }

    private MemberJoinRequestDto dummyMemberData() {
        String email = "dummy@gmail.com";
        String password = "dummydummy";
        String nickname = "피오니";
        return new MemberJoinRequestDto(email, password, nickname);
    }

    private Post basicPostData(Member member) {
        String title = "피에스타";
        String content = "내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member.getEmail(), writeTime, views);
    }

}