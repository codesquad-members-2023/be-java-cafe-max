package kr.codesqaud.cafe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.Post;
import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.repository.member.MemberRepository;
import kr.codesqaud.cafe.repository.post.PostRepository;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.service.PostService;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
    void posts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/all"))
                .andDo(print());
    }


    @Test
    void write() throws Exception {
        MemberJoinRequestDto requestDtoMember = dummyMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();

        Long savedPostId = postRepository.save(dummyPostData(member), member);
        PostResponse postResponse = postService.findById(savedPostId);

        //when,then
        mockMvc.perform(post("/posts/write/")
                        .param("title", postResponse.getTitle())
                        .param("content", postResponse.getContent())
                        .param("writerId", String.valueOf(postResponse.getWriter().getWriterId()))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/write"))
                .andDo(print());
    }


    @Test
    void eachPost() throws Exception {
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();
        Long savedPostId = postRepository.save(basicPostData(member), member);

        mockMvc.perform(get("/posts/{postId}/", savedPostId))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post"))
                .andDo(print());
    }

    @Test
    void writeFormTest() throws Exception {
        mockMvc.perform(get("/posts/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/write"))
                .andDo(print());
    }

    private MemberJoinRequestDto basicMemberData() {
        String email = "test@gmail.com";
        String password = "testtest";
        String nickName = "chacha";
        return new MemberJoinRequestDto(email, password, nickName);
    }


    private Post basicPostData(Member member) {
        String title = "피에스타";
        String content = "내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member, writeTime, views);
    }

    private MemberJoinRequestDto dummyMemberData() {
        String email = "dummy@gmail.com";
        String password = "dummydummy";
        String nickName = "피오니";
        return new MemberJoinRequestDto(email, password, nickName);
    }

    private Post dummyPostData(Member member) {
        String title = "러브다이브";
        String content = "니가 참 궁금해 그건 너도 마찬가지 이거면 충분해";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member, writeTime, views);
    }
}