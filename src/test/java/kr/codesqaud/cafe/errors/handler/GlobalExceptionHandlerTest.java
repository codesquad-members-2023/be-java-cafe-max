package kr.codesqaud.cafe.errors.handler;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("서버에 없는 URL 페이지가 주어지고 리소스 요청시 에러 페이지가 반환되는지 테스트")
    public void handle404() throws Exception {
        //given
        String url = "/iaowjefiojwef";
        //when
        mockMvc.perform(get(url))
            .andExpect(status().isNotFound());
        //then
    }
}
