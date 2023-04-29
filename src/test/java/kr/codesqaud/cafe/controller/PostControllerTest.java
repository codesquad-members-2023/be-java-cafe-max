package kr.codesqaud.cafe.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Stream;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.comment.CommentsResponse;
import kr.codesqaud.cafe.dto.post.PostModifyRequest;
import kr.codesqaud.cafe.dto.post.PostResponse;
import kr.codesqaud.cafe.dto.post.WriterResponse;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.exception.post.PostNotFoundException;
import kr.codesqaud.cafe.service.CommentService;
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

    @MockBean
    private CommentService commentService;

    @DisplayName("로그인을 안하고 ROOT URL 접근하면 게시글 목록 조회 페이지로 리다이렉트 된다.")
    @Test
    void showMain() throws Exception {
        // given
        given(postService.findAll(1)).willReturn(Collections.emptyList());

        // when

        // then
        mockMvc.perform(get("/"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/posts"))
            .andDo(print());
    }

    @DisplayName("로그인을 했을 때 게시글 작성 페이지에 접근하면 게시글 작성 페이지로 이동한다")
    @Test
    void showWriteForm() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(get("/posts/write/form")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/postWrite"))
            .andDo(print());
    }

    @DisplayName("로그인을 하고 제목, 내용, 작성자를 입력할 때 게시글 작성을 하면 게시글 목록 페이지로 이동한다")
    @Test
    void write() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        given(postService.write(any())).willReturn(1L);

        // when

        // then
        mockMvc.perform(post("/posts/write")
                .param("title", "게시글 제목")
                .param("content", "게시글 내용")
                .param("writerId", "1")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("로그인을 하고 게시글 작성을 할 때 게시글 제목이 빈값이거나 2글자 미만 또는 50글자 초과하면 에러 메시지를 담고서 게시글 작성 페이지로 이동한다")
    @ParameterizedTest
    @MethodSource("provideValueForValidatorName")
    void writeFalse(String title, String error) throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(post("/posts/write")
                .param("title", title)
                .param("content", "게시글 내용")
                .param("writerId", "1")
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

    @DisplayName("로그인을 하고 게시글 작성을 할 때 게시글 내용이 빈값이거나 2글자 미만이면 에러 메시지를 담고서 게시글 작성 페이지로 이동한다")
    @ParameterizedTest
    @CsvSource(value = {",NotBlank", "호,Length"})
    void writeFalse2(String content, String error) throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(post("/posts/write")
                .param("title", "게시글 제목")
                .param("content", content)
                .param("writerId", "1")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name("post/postWrite"))
            .andExpect(model().attributeHasFieldErrorCode("postWriteRequest", "content", error))
            .andDo(print());
    }

    @DisplayName("로그인을 하고 게시글 조회할 때 게시글이 있는 아이디를 조회하면 게시글 상세 조회 페이지로 이동한다")
    @Test
    void showPost() throws Exception {
        // given
        PostResponse postResponse = createPostResponseDummy();
        AccountSession accountSession = new AccountSession(postResponse.getWriter().getId(), "만두");
        given(postService.findById(1L)).willReturn(postResponse);
        given(commentService.findAllByPostId(any(), any())).willReturn(new CommentsResponse(Collections.emptyList(), false));

        // when

        // then
        mockMvc.perform(get("/posts/{id}", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/post"))
            .andExpect(model().attribute("postResponse", postResponse))
            .andDo(print());
    }

    @DisplayName("로그인을 하고 게시글 상세 조회할 때 게시글이 없는 번호를 조회하면 에러 페이지로 이동한다")
    @Test
    void showPostFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        given(postService.findById(1L)).willThrow(PostNotFoundException.class);

        // when

        // then
        mockMvc.perform(get("/posts/{id}", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("글 수정 페이지에 접근할 때 로그인 회원과 글 작성자가 같다면 글 수정 페이지로 이동한다")
    @Test
    void showModifyForm() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        given(postService.findPostForModifying(1L, accountSession.getId()))
            .willReturn(new PostModifyRequest(1L, "제목", "내용"));

        // when

        // then
        mockMvc.perform(get("/posts/{id}/form", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("post/postModify"))
            .andDo(print());
    }

    @DisplayName("게시글 수정 페이지 접근할 때 로그인 회원과 게시글 작성자가 다르다면 에러페이지로 이동한다")
    @Test
    void showModifyFormFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(2L, "만두");
        given(postService.findPostForModifying(1L, accountSession.getId()))
            .willThrow(new UnauthorizedException());

        // when

        // then
        mockMvc.perform(get("/posts/{id}/form", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("로그인을 하고 게시글 수정 페이지 접근할  게시글이 없는 번호를 조회하면 에러 페이지로 이동한다")
    @Test
    void showModifyFormFalse2() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        given(postService.findPostForModifying(1L, accountSession.getId()))
            .willThrow(new PostNotFoundException());

        // when

        // then
        mockMvc.perform(get("/posts/{id}/form", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 수정할 때 로그인 회원과 게시글 작성자가 같다면 게시글이 수정되고 게시글 상세 조회 페이지로 이동한다")
    @Test
    void modify() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(put("/posts/{id}", 1L)
                .param("title", "제목")
                .param("content", "내용")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/posts/{id}"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 수정할 때 로그인 회원과 게시글 작성자가 같고 해당 게시글이 없다면 에러페이지로 이동한다")
    @Test
    void modifyFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        willThrow(new PostNotFoundException()).given(postService).modify(any(), any());

        // when

        // then
        mockMvc.perform(put("/posts/{id}", 1L)
                .param("title", "제목")
                .param("content", "내용")
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 수정할 때 로그인 회원과 게시글 작성자가 다르면 에러 페이지로 이동한다")
    @Test
    void modifyFalse2() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(2L, "만두");
        willThrow(new UnauthorizedException()).given(postService).modify(any(), any());

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

    @DisplayName("로그인 하고 게시글 삭제할 때 로그인 회원과 게시글 작성자가 같다면 게시글이 삭제되고 게시글 목록 페이지로 이동한다")
    @Test
    void deletePost() throws Exception {
        // given
        Long id = 1L;
        AccountSession accountSession = new AccountSession(1L, "만두");

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", id)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 삭제할 때 해당 게시글이 없다면 에러 페이지로 이동한다")
    @Test
    void deleteFalse() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        willThrow(new PostNotFoundException()).given(postService).delete(1L, accountSession.getId());

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isNotFound())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/404"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 삭제할 때 로그인 회원과 게시글 작성자가 다르다면 에러 페이지로 이동한다")
    @Test
    void deleteFalse2() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        willThrow(new UnauthorizedException()).given(postService).delete(1L, accountSession.getId());

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    @DisplayName("로그인 하고 게시글 삭제할 때 로그인 회원과 게시글 작성자가 같고 댓글 작성자와 게시글 작성자가 다르다면 에러 페이지로 이동한다")
    @Test
    void deleteFalse3() throws Exception {
        // given
        AccountSession accountSession = new AccountSession(1L, "만두");
        willThrow(new UnauthorizedException("게시글 작성자와 댓글 작성자가 다릅니다.")).given(postService)
            .delete(1L, accountSession.getId());

        // when

        // then
        mockMvc.perform(delete("/posts/{id}", 1L)
                .sessionAttr(SignInSessionUtil.SIGN_IN_SESSION_NAME, accountSession))
            .andExpect(status().isUnauthorized())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(view().name("error/4xx"))
            .andDo(print());
    }

    private PostResponse createPostResponseDummy() {
        return new PostResponse(1L, "제목", "내용", new WriterResponse(1L, "만두"), LocalDateTime.now(),
            0L, 0);
    }

    private PostResponse createPostResponseDummy2() {
        return new PostResponse(2L, "제목2", "내용2", new WriterResponse(2L, "만두2"), LocalDateTime.now(),
            0L, 0);
    }
}
