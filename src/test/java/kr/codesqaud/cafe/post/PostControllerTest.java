package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.account.UserService;
import kr.codesqaud.cafe.account.dto.JoinForm;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    private static final String JACK = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    private static final String TITLE = "title";
    private static final String TEXT_CONTENT = "textContent";
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_CONTENT = "testContent";
    public static final int NON_EXISTING_POST_ID = 300;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

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

    @DisplayName("게시글 작성 페이지 열람")
    @Test
    void showPostPage() throws Exception {
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
            assertThat(postRepository.findByTitle(TEST_TITLE)).isPresent();
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
                    .andExpect(view().name("/post/form"));
            assertThat(postRepository.findByTitle(TEST_TITLE)).isEmpty();
        }
    }

    @DisplayName("지정 게시를 알람")
    @Nested
    class PostPageTest {

        @DisplayName("성공")
        @Test
        void testShowPostPageSuccess() throws Exception {
            Post post = postRepository.save(new Post.Builder()
                    .title(TEST_TITLE)
                    .textContent(TEST_CONTENT)
                    .build());

            mockMvc.perform(get("/posts/" + post.getId()).session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("post"));
        }

        @DisplayName("실패")
        @Test
        void testShowPostPageFailed() throws Exception {
            Post post = postRepository.save(new Post.Builder()
                    .title(TEST_TITLE)
                    .textContent(TEST_CONTENT)
                    .build());

            mockMvc.perform(get("/posts/" + NON_EXISTING_POST_ID).session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("error/4xx"));
        }
    }

}
