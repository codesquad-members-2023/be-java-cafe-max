package kr.codesquad.cafe.post;

import kr.codesquad.cafe.user.User;
import kr.codesquad.cafe.user.UserService;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.post.dto.PostForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    private static final String JACK = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String JERRY = "jerry";
    private static final String JERRY_EMAIL = "jerry@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    private static final String TITLE = "title";
    private static final String TEXT_CONTENT = "textContent";
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_CONTENT = "testContent";
    public static final int NON_EXISTING_POST_ID = 300;
    public static final String EDIT_TITLE = "editTitle";
    public static final String EDIT_TEXT_CONTENT = "editTextContent";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    MockHttpSession session;

    User jack;


    @BeforeEach
    void setSession() {
        JoinForm joinForm = new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
        jack = userService.save(joinForm);
        session = new MockHttpSession();
        session.setAttribute("user", jack);
    }

    @DisplayName("게시글 작성 페이지 오픈")
    @Test
    void viewPostPage() throws Exception {
        mockMvc.perform(get("/posts/form")
                        .session(session))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 추가 버튼 테스트")
    @Nested
    class PostAddBottomTest {
        @DisplayName("성공")
        @Test
        void addPostSuccess() throws Exception {
            mockMvc.perform(post("/posts")
                            .param(TITLE, TEST_TITLE)
                            .param(TEXT_CONTENT, TEST_CONTENT)
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("/posts/*"));
        }

        @DisplayName("실패")
        @ParameterizedTest
        @CsvSource({"t,textContent", "title,te"})
        void addPostFailureFailed(String title, String textContent) throws Exception {
            mockMvc.perform(post("/posts")
                            .param(TITLE, title)
                            .param(TEXT_CONTENT, textContent)
                            .session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("post/form"));
        }
    }

    @DisplayName("지정 게시글")
    @Nested
    class PostPageTest {

        Post testPost;
        PostForm testForm;

        @BeforeEach
        void setPost() {
            testForm = new PostForm(TEST_TITLE, TEST_CONTENT);
            testPost = postService.save(testForm, jack);
        }

        @DisplayName("오픈")
        @Nested
        class OpenTest {
            @DisplayName("성공")
            @Test
            void viewPostPageSuccess() throws Exception {
                mockMvc.perform(get("/posts/" + testPost.getId())
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists("post"));
            }

            @DisplayName("실패")
            @Test
            void viewPostPageFailed() throws Exception {
                mockMvc.perform(get("/posts/" + NON_EXISTING_POST_ID)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }
        }

        @DisplayName("지정 게스글 수정 페이지")
        @Nested
        class EditTest {
            @DisplayName("작성자 오픈 성공")
            @Test
            void viewPostEditPageSuccess() throws Exception {
                mockMvc.perform(get("/posts/" + testPost.getId() + "/edit")
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post/editForm"));
            }

            @DisplayName("오픈 실패(작성자 오류)")
            @Test
            void viewPostEditPageFailedByOtherUser() throws Exception {
                setSessionByJerry();

                mockMvc.perform(get("/posts/" + testPost.getId() + "/edit")
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }


            @DisplayName("수정 성공")
            @Test
            void editPostSuccess() throws Exception {
                mockMvc.perform(put("/posts/" + testPost.getId())
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post/detail"));
                Post post = postService.findById(testPost.getId());
                assertThat(post.getTitle()).isEqualTo(EDIT_TITLE);
                assertThat(post.getTextContent()).isEqualTo(EDIT_TEXT_CONTENT);
            }


            @DisplayName("수정 실패(형식 오류)")
            @ParameterizedTest
            @CsvSource({"t,textContent", "title,te"})
            void editPostFailedByType(String targetTitle, String targetContent) throws Exception {

                mockMvc.perform(put("/posts/" + testPost.getId())
                                .param(TITLE, targetTitle)
                                .param(TEST_CONTENT, targetContent)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(view().name("post/editForm"));
            }

            @DisplayName("수정 실패(없는 Post 아이디)")
            @Test
            void editPostFailedByPostId() throws Exception {
                mockMvc.perform(put("/posts/" + NON_EXISTING_POST_ID)
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("수정 실패(없는 아이디)")
            @Test
            void editPostFailedByUser() throws Exception {
                setSessionByJerry();
                mockMvc.perform(put("/posts/" + testPost.getId())
                                .param(TITLE, EDIT_TITLE)
                                .param(TEXT_CONTENT, EDIT_TEXT_CONTENT)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }


            @DisplayName("삭제 실패")
            @Test
            void deletePostFailed() throws Exception {
                setSessionByJerry();
                mockMvc.perform(delete("/posts/" + testPost.getId())
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("삭제 성공")
            @Test
            void deletePostSuccess() throws Exception {
                mockMvc.perform(delete("/posts/" + testPost.getId())
                                .session(session))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/"));
            }

            private void setSessionByJerry() {
                JoinForm joinForm = new JoinForm(JERRY, JERRY_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
                User jerry = userService.save(joinForm);
                session = new MockHttpSession();
                session.setAttribute("user", jerry);
            }
        }
    }

}
