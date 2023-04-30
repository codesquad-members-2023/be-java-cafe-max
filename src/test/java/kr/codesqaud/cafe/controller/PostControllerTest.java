package kr.codesqaud.cafe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
import kr.codesqaud.cafe.session.LoginMemberSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


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

        Long savedId = postRepository.save(dummyPostData(member), member);
        PostResponse postResponse = postService.findById(savedId);

        LoginMemberSession loginMemberSession = new LoginMemberSession(dummyMemberData().getEmail(),savedId);

        //when,then
        mockMvc.perform(post("/posts/write/")
                        .param("title", postResponse.getTitle())
                        .param("content", postResponse.getContent())
                        .param("writerEmail", postResponse.getWriter().getWriterEmail())
                        .sessionAttr("loginMember", loginMemberSession)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/posts"))
                .andDo(print());
    }


    @Test
    void eachPost() throws Exception {
        MemberJoinRequestDto requestDtoMember = basicMemberData();
        Long memberId = memberService.join(requestDtoMember);
        Member member = memberRepository.findById(memberId).orElseThrow();
        Long savedId = postRepository.save(basicPostData(member), member);

        //when
        MockHttpSession httpSession = new MockHttpSession();
        httpSession.setAttribute("loginMember", new LoginMemberSession(member.getEmail(),memberId));

        mockMvc.perform(get("/posts/{id}", savedId).session(httpSession))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post"))
                .andExpect(model().attributeExists("postResponse"))
                .andExpect(model().attributeExists("commentListDto"));

        //then
        PostResponse postResponse = (PostResponse) mockMvc.perform(get("/posts/{id}", savedId).session(httpSession))
                .andReturn().getModelAndView().getModel().get("postResponse");
        assertThat(postResponse.getTitle()).isEqualTo("피에스타");
        assertThat(postResponse.getContent()).isEqualTo("내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게");
        assertThat(postResponse.getWriter().getWriterEmail()).isEqualTo("test@gmail.com");
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
        String nickname = "chacha";
        return new MemberJoinRequestDto(email, password, nickname);
    }


    private Post basicPostData(Member member) {
        String title = "피에스타";
        String content = "내맘에 태양을 꼭 삼킨채 영원토록 뜨겁게 지지 않을게";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member.getEmail(), writeTime, views);
    }

    private MemberJoinRequestDto dummyMemberData() {
        String email = "dummy@gmail.com";
        String password = "dummydummy";
        String nickname = "피오니";
        return new MemberJoinRequestDto(email, password, nickname);
    }

    private Post dummyPostData(Member member) {
        String title = "러브다이브";
        String content = "니가 참 궁금해 그건 너도 마찬가지 이거면 충분해";
        LocalDateTime writeTime = LocalDateTime.now();
        Long views = 0L;
        return new Post(title, content, member.getEmail(), writeTime, views);
    }
}
