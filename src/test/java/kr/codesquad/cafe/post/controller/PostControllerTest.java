package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.global.auth.AuthBeforeAdvice;
import kr.codesquad.cafe.global.exception.IllegalAccessIdException;
import kr.codesquad.cafe.post.Post;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.dto.PostForm;
import kr.codesquad.cafe.post.exception.DeletionFailedException;
import kr.codesquad.cafe.post.exception.PostNotFoundException;
import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
@Import({AopAutoConfiguration.class, AuthBeforeAdvice.class})
class PostControllerTest {

    public static final int NOT_EXISTING_POST_ID = 300;
    public static final String EDIT_TITLE = "editTitle";
    public static final String EDIT_TEXT_CONTENT = "editTextContent";
    private static final String TITLE = "title";
    private static final String TEXT_CONTENT = "textContent";
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_CONTENT = "testContent";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private MockHttpSession session;

    User user;

    @BeforeEach
    void setSession() {
        user = mock(User.class);
        session = new MockHttpSession();
        session.setAttribute("user", user);
    }

    @DisplayName("게시글 작성 페이지")
    @Nested
    class PostPage {
        @DisplayName("유저이면 게시글 작성 페이지 접근 가능")
        @Test
        void viewPostPageSuccess() throws Exception {
            mockMvc.perform(get("/posts/form")
                            .session(session))
                    .andExpect(status().isOk());
        }

        @DisplayName("유저가 아니면 게시글 작성 페이지 접근 불가능하고 에러 로그인 페이를 보여준다")
        @Test
        void viewPostPageFailed() throws Exception {
            mockMvc.perform(get("/posts/form"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/login"));
        }
    }

    @DisplayName("게시글 추가 버튼 테스트")
    @Nested
    class PostAddBottomTest {
        @DisplayName("형식도 맞고 접근 권한도 있으면 정상으로 저장하고 포스트 페이지를 보여준다")
        @Test
        void addPostSuccess() throws Exception {
            Post post = mock(Post.class);
            given(postService.save(any(PostForm.class), any(User.class))).willReturn(post);
            given(post.getId()).willReturn(1L);

            mockMvc.perform(post("/posts")
                            .param(TITLE, TEST_TITLE)
                            .param(TEXT_CONTENT, TEST_CONTENT)
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/posts/" + post.getId()));
        }

        @DisplayName("형식이 맞지 않으면 에러 메시지와 함께 포스트 폼 화면을 보여준다")
        @ParameterizedTest
        @CsvSource({"t,textContent", "title,te"})
        void addPostFailureFailedByType(String title, String textContent) throws Exception {
            mockMvc.perform(post("/posts")
                            .param(TITLE, title)
                            .param(TEXT_CONTENT, textContent)
                            .session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("post/form"));
        }

        @DisplayName("유저가 아니면 로그인 화면을 보여준다")
        @Test
        void addPostFailureFailedByNotUser() throws Exception {
            mockMvc.perform(post("/posts")
                            .param(TITLE, TEST_TITLE)
                            .param(TEXT_CONTENT, TEST_CONTENT))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/login"));
        }
    }

    @DisplayName("지정 게시글")
    @Nested
    class PostPageTest {

        @DisplayName("접근")
        @Nested
        class OpenTest {
            @DisplayName("유저이면 포스트 페이지를 보여준다")
            @Test
            void viewPostPageSuccess() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                given(postService.findById(anyLong())).willReturn(post);

                mockMvc.perform(get("/posts/" + post.getId())
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists("post"))
                        .andExpect(view().name("post/detail"));
            }

            @DisplayName("존재하지 페이지 에러 발생시 오류 페이지를 보여준다")
            @Test
            void viewPostPageFailedByPageId() throws Exception {
                given(postService.findById(anyLong())).willThrow(new PostNotFoundException());

                mockMvc.perform(get("/posts/" + NOT_EXISTING_POST_ID)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("유저가 아니면 로그인 페이지를 보여준다")
            @Test
            void viewPostPageFailedByUser() throws Exception {
                mockMvc.perform(get("/posts/" + NOT_EXISTING_POST_ID))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/users/login"));
            }
        }

        @DisplayName("지정 게스글 수정 페이지")
        @Nested
        class EditTest {

            @DisplayName("수정페이지에 접근이 가능하면 보여준다")
            @Test
            void viewPostEditPageSuccess() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                given(postService.findByIdForEditForm(anyLong(), anyLong())).willReturn(post);

                mockMvc.perform(get("/posts/" + post.getId() + "/editForm")
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post/editForm"));
            }

            @DisplayName("수정 페이지에 접근이 불가능하면 에러 페이지를 보여준다")
            @Test
            void viewPostEditPageFailedByOtherUser() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                given(postService.findByIdForEditForm(anyLong(), anyLong())).willThrow(new IllegalAccessIdException());

                mockMvc.perform(get("/posts/" + post.getId() + "/editForm")
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("포스트 수정 성공하면 다시 포스트 화면을 보여준다")
            @Test
            void editPostSuccess() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                given(postService.updateFromPostForm(anyLong(), any(PostForm.class), anyLong())).willReturn(post);

                mockMvc.perform(put("/posts/" + post.getId())
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post/detail"));
            }


            @DisplayName("입력 한 정보가 형식 오류가 있으면 오류와 함께 수정페이지를 보여준다")
            @ParameterizedTest
            @CsvSource({"t,textContent", "title,te"})
            void editPostFailedByType(String targetTitle, String targetContent) throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);

                mockMvc.perform(put("/posts/" + post.getId())
                                .param(TITLE, targetTitle)
                                .param(TEST_CONTENT, targetContent)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().hasErrors())
                        .andExpect(view().name("post/editForm"));
            }

            @DisplayName("존재하지 않는 포스트 아이디 에러가 발새하면 에러 페이지를 보여준다")
            @Test
            void editPostFailedByPostId() throws Exception {
                given(postService.updateFromPostForm(anyLong(), any(), anyLong()))
                        .willThrow(IllegalAccessIdException.class);

                mockMvc.perform(put("/posts/" + NOT_EXISTING_POST_ID)
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("접근 불가능한 포스트 에러가 발생하면 에러 페이지를 보여준다")
            @Test
            void editPostFailedByUser() throws Exception {
                Post post = mock(Post.class);
                given(postService.updateFromPostForm(anyLong(), any(), anyLong()))
                        .willThrow(IllegalAccessIdException.class);

                mockMvc.perform(put("/posts/" + post.getId())
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }


            @DisplayName("포스트 삭제권한은 있지만 다른 맴버의 댓글이 있어서 에러가 발생시 에러 메시지와 함께 포스트 에디터폼을 보여준다")
            @Test
            void deletePostFailedByOtherComments() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                given(post.getTitle()).willReturn(TEST_TITLE);
                given(post.getTextContent()).willReturn(TEST_CONTENT);
                doThrow(DeletionFailedException.class).when(postService).delete(anyLong(), anyLong());
                given(postService.findById(anyLong())).willReturn(post);

                mockMvc.perform(delete("/posts/" + post.getId())
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("post/editForm"));
            }

            @DisplayName("존재 하지 않는 포스트를 삭제 요청시 에러 페이지를 보여준다")
            @Test
            void deletePostFailedByNotExistPost() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                doThrow(PostNotFoundException.class).when(postService).delete(anyLong(), anyLong());

                mockMvc.perform(delete("/posts/" + post.getId())
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("삭제 권한이 없는 포스트를 삭제 요청시 에러 페이지를 보여준다")
            @Test
            void deletePostFailedByAuth() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);
                doThrow(IllegalAccessIdException.class).when(postService).delete(anyLong(), anyLong());

                mockMvc.perform(delete("/posts/" + post.getId())
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("포스트 삭제 성공하면 루트페이지로 리다이렉트한다")
            @Test
            void deletePostSuccess() throws Exception {
                Post post = mock(Post.class);
                given(post.getId()).willReturn(1L);

                mockMvc.perform(delete("/posts/" + post.getId())
                                .session(session))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }

        }
    }

}
