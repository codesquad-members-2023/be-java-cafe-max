package codesquad.cafe.controller;

import codesquad.cafe.domain.user.domain.User;
import codesquad.cafe.domain.user.repository.MemoryUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemoryUserRepository userRepository;

    @Test
    @DisplayName("[GET] /users/join 경로로 이동하면 /user/form.html 보여주기 테스트")
    void showJoinForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/join"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/form"));
    }

    @Test
    @DisplayName("[POST] /users 로 이동하면 회원 가입 데이터 받고 GET /users로 redirect하기 테스트")
    void registerUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "sio")
                        .param("password", "1234")
                        .param("name", "sio")
                        .param("email", "sio@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));
    }

    @Test
    @DisplayName("[GET] /users로 이동하면 user/list 화면 보여주면서 회원 목록 출력하기 테스트")
    void showUsers() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.view().name("user/list"));
    }

    @Test
    @DisplayName("[GET] /users/{userId} 로 이동하면 user 객체 찾아서 user/profile 화면에 출력하기 테스트")
    void showProfile() throws Exception {
        saveDummyUser();
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/{userId}", "sio"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("user/profile"));
    }

    @Test
    @DisplayName("[GET] /users/{userId}/form 으로 이동하면 userId를 넘겨주면서 user/updateForm 화면을 출력하기 테스트")
    void showUpdateForm() throws Exception {
        saveDummyUser();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/users/{userId}/form", "sio"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("userId"))
                .andExpect(MockMvcResultMatchers.view().name("user/updateForm"));
    }

    @Test
    @DisplayName("[PUT] /users/{userId}/update 로 이동하면 수정할 유저 정보와 userId를 넘겨주고 redirect:/user 출력하기 테스트")
    void updateUser() throws Exception {
        saveDummyUser();

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{userId}/update", "sio")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", "1234")
                        .param("updatePassword", "1111")
                        .param("name", "sio")
                        .param("email", "sio@gmail.com"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users"));

    }

    private void saveDummyUser() {
        userRepository.save(new User("sio", "1234", "sio", "sio@gmail.com"));
    }
}