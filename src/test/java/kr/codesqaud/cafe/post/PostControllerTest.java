package kr.codesqaud.cafe.post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    public static final String NICKNAME = "nickname";
    public static final String TITLE = "title";
    public static final String TEXT_CONTENT = "textContent";
    public static final String JACK = "jack";
    public static final String TEST_TITLE = "testTitle";
    public static final String TEST_CONTENT = "testContent";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @DisplayName("게시글 작성 페이지 열람")
    @Test
    void showPostPage() throws Exception {
        mockMvc.perform(get("/posts/form"))
                .andExpect(status().isOk());
    }

    @DisplayName("게시글 추가 버튼 테스트")
    @Nested
    class PostAddBottomTest {
        @DisplayName("성공")
        @Test
        void addPostSuccess() throws Exception {
            String testTitle = TEST_TITLE;
            mockMvc.perform(post("/posts")
                            .param(NICKNAME, JACK)
                            .param(TITLE, testTitle)
                            .param(TEXT_CONTENT, TEST_CONTENT))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("post"))
                    .andExpect(view().name("/post/postDetail"));

            assertThat(postRepository.findByTitle(testTitle)).isPresent();
        }

        @DisplayName("실패")
        @ParameterizedTest
        @CsvSource({"j,testTitle,testContent", "jack,t,textContent", "jack,title,te"})
        void addPostFailureFailed(String nickname, String title, String textContent) throws Exception {
            String testTitle = TEST_TITLE;
            mockMvc.perform(post("/posts")
                            .param(NICKNAME, nickname)
                            .param(TITLE, title)
                            .param(TEXT_CONTENT, textContent))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("/post/form"));

            assertThat(postRepository.findByTitle(testTitle)).isEmpty();
        }
    }

    @DisplayName("지정 게시를 얄람")
    @Nested
    class PostPageTest {

        @DisplayName("성공")
        @Test
        void testShowPostPageSuccess() throws Exception {
            int savedId = postRepository.save(new Post.Builder()
                    .nickname(JACK)
                    .title(TEST_TITLE)
                    .textContent(TEST_CONTENT)
                    .build());

            mockMvc.perform(get("/posts/" + savedId))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists("post"));
        }

        @DisplayName("실패")
        @Test
        void testShowPostPageFailed() throws Exception {
            int savedId = postRepository.save(new Post.Builder()
                    .nickname(JACK)
                    .title(TEST_TITLE)
                    .textContent(TEST_CONTENT)
                    .build());
            mockMvc.perform(get("/posts/" + (++savedId)))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("error/custom"));
        }
    }

}
