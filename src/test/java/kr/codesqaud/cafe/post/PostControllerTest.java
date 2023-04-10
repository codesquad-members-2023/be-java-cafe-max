package kr.codesqaud.cafe.post;

import kr.codesqaud.cafe.account.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class PostControllerTest {

    private static final String NICKNAME = "nickname";
    private static final String TITLE = "title";
    private static final String TEXT_CONTENT = "textContent";
    private static final String JACK = "jack";
    private static final String TEST_TITLE = "testTitle";
    private static final String TEST_CONTENT = "testContent";
    public static final String USER = "user";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    MockHttpSession session;


    @BeforeEach
    void setSession() {
        User user = mock(User.class);
        Mockito.when(user.getNickname()).thenReturn(JACK);
        session = new MockHttpSession();
        session.setAttribute(USER, user);
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
                            .param(NICKNAME, JACK)
                            .param(TITLE, TEST_TITLE)
                            .param(TEXT_CONTENT, TEST_CONTENT)
                            .session(session))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("/posts/*"));
            assertThat(postRepository.findByTitle(TEST_TITLE)).isPresent();
        }

        @DisplayName("실패")
        @ParameterizedTest
        @CsvSource({"j,testTitle,testContent", "jack,t,textContent", "jack,title,te"})
        void addPostFailureFailed(String nickname, String title, String textContent) throws Exception {
            mockMvc.perform(post("/posts")
                            .param(NICKNAME, nickname)
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
                    .nickname(JACK)
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
                    .nickname(JACK)
                    .title(TEST_TITLE)
                    .textContent(TEST_CONTENT)
                    .build());

            mockMvc.perform(get("/posts/" + (post.getId() + 1)).session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("error/4xx"));
        }
    }

}
