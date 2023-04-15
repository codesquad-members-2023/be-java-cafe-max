package kr.codesqaud.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import kr.codesqaud.cafe.exception.common.Unauthorized;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.PostWriteRequest;
import kr.codesqaud.cafe.dto.post.WriterResponse;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.service.PostService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @DisplayName("게시물 목록 조회")
    @Test
    void posts() throws Exception {
        // given
        List<PostResponse> postResponses = List.of(createPostResponseDummy(),
            createPostResponseDummy2());
        given(postService.findAll()).willReturn(postResponses);

        // when

        // then
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/posts"))
            .andExpect(model().attribute("postResponses", postResponses))
            .andDo(print());
    }

    @DisplayName("게시글 작성 성공")
    @Test
    void write() throws Exception {
        // given
        Long writerId = 1L;
        AccountSession accountSession = new AccountSession(writerId);
        PostWriteRequest postWriteRequest = new PostWriteRequest("게시글 제목", "게시글 내용", writerId);
        given(postService.write(any(), any())).willReturn(writerId);

        // when

        // then
        mockMvc.perform(post("/posts")
                .param("title", postWriteRequest.getTitle())
                .param("content", postWriteRequest.getContent())
                .param("writerId", String.valueOf(postWriteRequest.getWriterId()))
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("게시글 작성시 게시글 제목이 빈값이거나 2글자 미만 또는 50글자 초과하는 경우 실패")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorName")
    void writeFalse(String title, String error) throws Exception {
        // given
        Long writerId = 1L;
        AccountSession accountSession = new AccountSession(writerId);
        PostWriteRequest postWriteRequest = new PostWriteRequest(title, "게시글 내용", writerId);

        // when

        // then
        mockMvc.perform(post("/posts")
                .param("title", postWriteRequest.getTitle())
                .param("content", postWriteRequest.getContent())
                .param("writerId", String.valueOf(postWriteRequest.getWriterId()))
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postWrite"))
            .andExpect(model().attributeHasFieldErrorCode("postWriteRequest", "title", error))
            .andDo(print());
    }

    private static Stream<Arguments> provideValueForValidatorName() {
        return Stream.of(Arguments.of(null, "NotBlank"),
            Arguments.of("호", "Length"),
            Arguments.of("1".repeat(51), "Length"));
    }

    @DisplayName("게시글 작성시 게시글 내용이 빈값이거나 2글자 미만인 경우 실패")
    @ParameterizedTest
    @CsvSource(value = {",NotBlank", "호,Length"})
    void writeFalse2(String content, String error) throws Exception {
        // given
        Long writerId = 1L;
        AccountSession accountSession = new AccountSession(writerId);
        PostWriteRequest postWriteRequest = new PostWriteRequest("게시글 제목", content, writerId);

        // when

        // then
        mockMvc.perform(post("/posts")
                .param("title", postWriteRequest.getTitle())
                .param("content", postWriteRequest.getContent())
                .param("writerId", String.valueOf(postWriteRequest.getWriterId()))
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postWrite"))
            .andExpect(model().attributeHasFieldErrorCode("postWriteRequest", "content", error))
            .andDo(print());
    }

    @DisplayName("게시글 단건 조회 성공")
    @Test
    void detailPost() throws Exception {
        // given
        PostResponse postResponse = createPostResponseDummy();
        AccountSession accountSession = new AccountSession(postResponse.getId());
        given(postService.findById(any())).willReturn(postResponse);

        // when

        // then
        mockMvc.perform(get("/posts/{id}", postResponse.getId())
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/post"))
            .andExpect(model().attribute("postResponse", postResponse))
            .andDo(print());
    }

    @DisplayName("게시글 단건 조회시 게시글 아이디가 없는 경우 실패")
    @Test
    void detailPostFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L);
        given(postService.findById(any())).willThrow(PostNotFoundException.class);

        // when

        // then
        mockMvc.perform(get("/posts/{id}", "1")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("글쓰기 폼 페이지")
    @Test
    void writeForm() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L);

        // when

        // then
        mockMvc.perform(get("/posts/write")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/postWrite"))
            .andDo(print());
    }

    @DisplayName("글 수정 폼 페이지")
    @Test
    void modifyForm() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);
        given(postService.findById(id)).willReturn(createPostResponseDummy());

        // when

        // then
        mockMvc.perform(get("/posts/{id}/modify", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/postModify"))
            .andDo(print());
    }

    @DisplayName("글 수정 폼 페이지 접속시 글 작성자와 로그인한 회원과 다를 경우 실패")
    @Test
    void modifyFormFalse() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(2L);
        willThrow(new Unauthorized()).given(postService).validateUnauthorized(id, accountSession);

        // when

        // then
        mockMvc.perform(get("/posts/{id}/modify", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("글 수정 폼 페이지 접속시 해당 게시글이 없는 경우 실패")
    @Test
    void modifyFormFalse2() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);
        given(postService.findById(id)).willThrow(new PostNotFoundException());

        // when

        // then
        mockMvc.perform(get("/posts/{id}/modify", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("글 수정 성공")
    @Test
    void modify() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);

        // when

        // then
        mockMvc.perform(put("/posts/{id}", id)
                .param("title", "제목")
                .param("content", "내용")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/posts/{id}"))
            .andDo(print());
    }

    @DisplayName("글 수정시 해당 게시글이 없는 경우 실패")
    @Test
    void modifyFalse() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);
        willThrow(new PostNotFoundException()).given(postService).modify(any(), any());

        // when

        // then
        mockMvc.perform(put("/posts/{id}", id)
                .param("title", "제목")
                .param("content", "내용")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("글 수정시 글 작성자와 로그인한 회원과 다를 경우 실패")
    @Test
    void modifyFalse2() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(2L);
        willThrow(new Unauthorized()).given(postService).validateUnauthorized(id, accountSession);

        // when

        // then
        mockMvc.perform(put("/posts/{id}", id)
                .param("title", "제목")
                .param("content", "내용")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("게시글 삭제 성공")
    @Test
    void deletePost() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("게시글 삭제시 게시글이 없는 경우 실패")
    @Test
    void deleteFalse() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);
        willThrow(new PostNotFoundException()).given(postService).delete(id);

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("게시글 삭제시 글 작성자와 로그인한 회원과 다를 경우 실패")
    @Test
    void deleteFalse2() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L);
        willThrow(new Unauthorized()).given(postService).validateUnauthorized(id, accountSession);

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    private PostResponse createPostResponseDummy() {
        return new PostResponse(1L, "제목", "내용", new WriterResponse(1L, "만두"), LocalDateTime.now(),
            0L);
    }

    private PostResponse createPostResponseDummy2() {
        return new PostResponse(2L, "제목2", "내용2", new WriterResponse(2L, "만두2"), LocalDateTime.now(),
            0L);
    }
}
