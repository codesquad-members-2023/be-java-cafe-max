package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @DisplayName("메인 페이지는 모든 post를 Pagable를 사용하여 보여 준다")
    @Test
    void viewIndex() throws Exception {
        SimplePostForm simplePostForm = mock(SimplePostForm.class);
        PagesInfo pagesInfo = mock(PagesInfo.class);
        given(postService.getAllSimplePostForm(anyInt())).willReturn(List.of(simplePostForm));
        given(postService.getPagesInfo(anyInt())).willReturn(pagesInfo);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("simpleForms", "pagesInfo"));
    }
}
