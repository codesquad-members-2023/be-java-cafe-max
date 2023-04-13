package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.controller.dto.user.UserDTO;
import kr.codesqaud.cafe.controller.dto.user.UserListDTO;
import kr.codesqaud.cafe.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입시 db에 회원을 저장한후 user/list 로 리다이렉트한다.")
    void signUpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/sign-up")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nickName", "nickName")
                        .param("email", "aaaa@naver.com")
                        .param("password","password123")
                        .param("id","charlie"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @DisplayName("회원가입 성공시 회원들의 list 를 user/list 에서 나열한다.")
    void showUserListTest() throws Exception {
        //given
        List<UserListDTO> userList = new ArrayList<>();
        given(userService.getUserList()).willReturn(userList);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"))//객체 검증
                .andExpect(model().attribute("users",userList));
    }

    @Test
    @DisplayName("유저의 정보를 db로부터 가져와 볼수있다.")
    void showUserProfileTest() throws Exception {
        //given
        UserDTO userDTO = new UserDTO("nickName", "aaa@naver.com", "password123", "testId");
        given(userService.getUserById("testId")).willReturn(userDTO);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile/testId"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attributeExists("user"))//객체 검증
                .andExpect(model().attribute("user",userDTO));

    }

    @Test
    @DisplayName("유저의 정보를 수정할수있는 id가 입력된상태의 form으로 이동한다.")
    void userUpdateFormTest() throws Exception {
        //given
        UserDTO userDTO = new UserDTO("nickName", "aaa@naver.com", "password123", "testId");
        given(userService.getUserById("testId")).willReturn(userDTO);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile/testId/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/updateForm"))
                .andExpect(model().attributeExists("user"))//객체 검증
                .andExpect(model().attribute("user",userDTO));
    }

    @Test
    @DisplayName("유저의 정보를 수정하면 db에 정보를 업데이트후 멤버 리스트로 리다이렉트 한다.")
    void updateUserDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/user/profile/testId")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("nickName", "charlie")
                        .param("email", "aaaa@naver.com")
                        .param("oriPassword","password123")
                        .param("newPassword","newpassword123")
                        .param("id","testId"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/users"));

    }
}