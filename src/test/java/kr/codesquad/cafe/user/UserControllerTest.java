package kr.codesquad.cafe.user;

import kr.codesquad.cafe.post.PostIdToPostConverter;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.user.domain.Role;
import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.exception.ExistsEmailException;
import kr.codesquad.cafe.user.exception.IncorrectPasswordException;
import kr.codesquad.cafe.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
class UserControllerTest {
    public static final String RECONFIRM_PASSWORD = "reconfirmPassword";
    private static final String JACK_NICKNAME = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    private static final String JERRY_EMAIL = "jerry@email.com";
    private static final String JERRY = "jerry";
    private static final String USER_ID = "userId";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PROFILE_EDIT_FORM = "profileEditForm";
    private static final String PROFILE_FORM = "profileForm";
    private static final String JOIN_FORM = "joinForm";
    private static final int NON_EXISTING_USER_ID = 200;
    private static final int NOT_EXIST_PAGE = 2000;
    private static final String NO_MATCH_PASSWORD = "12345678aa";
    private static final String ATTR_NAME_USER = "user";
    private static final String ATTR_NAME_USERS = "users";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostIdToPostConverter postIdToPostConverter;

    @MockBean
    private PostService postService;

    private MockHttpSession session;
    private User jack;

    @BeforeEach
    void setSession() {
        JoinForm joinForm = new JoinForm(JACK_NICKNAME, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
        jack = setJack();
        when(userService.save(joinForm)).thenReturn(jack);
        session = new MockHttpSession();
        session.setAttribute(ATTR_NAME_USER, jack);
    }

    private static User setJack() {
        return new User.Builder()
                .id(1L)
                .email(JACK_EMAIL)
                .nickname(JACK_NICKNAME)
                .email(JACK_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }

    @DisplayName("유저 리스트 페이지")
    @Nested
    class UsersPageTest {

        @DisplayName("Manager 일 때 오픈 가능")
        @Test
        void viewUsersSuccess() throws Exception {
            User manager = new User.Builder()
                    .id(jack.getId())
                    .role(Role.MANAGER)
                    .email(jack.getEmail())
                    .nickname(jack.getNickname())
                    .password(jack.getPassword())
                    .build();

            session.setAttribute(ATTR_NAME_USER, manager);
            mockMvc.perform(get("/users").session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists(ATTR_NAME_USERS))
                    .andExpect(view().name("user/users"));
        }

        @DisplayName("Manager 아닐 때 에러 발생")
        @Test
        void viewUsersFailedNotManager() throws Exception {
            session.setAttribute(ATTR_NAME_USER, jack);
            mockMvc.perform(get("/users").session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("error/4xx"));
        }

        @DisplayName("로그인 하지 아닐 때 에러 발생")
        @Test
        void viewUsersFailedNotUser() throws Exception {
            mockMvc.perform(get("/users"))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("error/4xx"));
        }
    }


    @DisplayName("로그인 페이지 테스트")
    @Nested
    class LoginTest {
        @DisplayName("누구나 오픈 가능")
        @Test
        void viewLoginPage() throws Exception {
            mockMvc.perform(get("/users/login"))
                    .andExpect(status().isOk());
        }

        @DisplayName("저장된 로그인 정보와 일치하면 개인 페이지로 이동")
        @Test
        void loginSuccess() throws Exception {
            given(userService.checkLoginForm(any())).willReturn(jack);
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, TEST_PASSWORD))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/" + jack.getId()));
        }

        @DisplayName("비밀번호 불 일치 예외 발생시 로그인 실패 페이지로 이동")
        @Test
        void loginFailedByPassword() throws Exception {
            given(userService.checkLoginForm(any())).willThrow(new IncorrectPasswordException());
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, NO_MATCH_PASSWORD).session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(model().attributeExists("passwordError"))
                    .andExpect(view().name("user/loginFailed"))
                    .andDo(print());
        }

        @DisplayName("존재 하지 않는 이메일 예외 발생시 로그인 실패 페이지로 이동")
        @Test
        void loginFailedByEmail() throws Exception {
            given(userService.checkLoginForm(any())).willThrow(new UserNotFoundException());
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, "jack1@email.com")
                            .param(PASSWORD, "12345ddd").session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(model().attributeExists("emailError"))
                    .andExpect(view().name("user/loginFailed"))
                    .andDo(print());
        }

    }

    @Nested
    @DisplayName("가입 페이지")
    class JoinTest {

        @DisplayName("누구나 오픈 가능")
        @Test
        void viewJoinPage() throws Exception {
            mockMvc.perform(get("/users/joinForm"))
                    .andExpect(model().attributeExists(JOIN_FORM))
                    .andExpect(status().isOk())
                    .andDo(print());
        }

        @DisplayName("유저 추가 성공하면 유저 info 페이지로 이동한다.")
        @Test
        void addUserSuccess() throws Exception {
            given(userService.save(any())).willReturn(jack);
            mockMvc.perform(post("/users")
                            .param(EMAIL, JACK_EMAIL)
                            .param(NICKNAME, JACK_NICKNAME)
                            .param(PASSWORD, TEST_PASSWORD)
                            .param(RECONFIRM_PASSWORD, TEST_PASSWORD))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/" + jack.getId()))
                    .andDo(print());
        }

        @DisplayName("입력 정보가 형식에 맞지 않으면 오류 메시지를 보여준다.")
        @ParameterizedTest
        @CsvSource({"sss," + JACK_NICKNAME + ",a123456789", JACK_EMAIL + ",j,a1223456789", JACK_EMAIL + "," + JACK_NICKNAME + ",123456789"})
        void addUserFailedByType(String email, String nickname, String password) throws Exception {
            given(userService.save(any())).willThrow(new ExistsEmailException());
            mockMvc.perform(post("/users")
                            .param(EMAIL, email)
                            .param(NICKNAME, nickname)
                            .param(PASSWORD, password)
                            .param(RECONFIRM_PASSWORD, password))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("user/join"));
        }

        @DisplayName("입력한 Email이 존재하면 ")
        @Test
        void addUserFailedByEmail() throws Exception {
            given(userService.save(any())).willThrow(new ExistsEmailException());
            mockMvc.perform(post("/users")
                            .param(EMAIL, JACK_EMAIL)
                            .param(NICKNAME, JACK_NICKNAME)
                            .param(PASSWORD, TEST_PASSWORD)
                            .param(RECONFIRM_PASSWORD, TEST_PASSWORD))
                    .andExpect(status().is4xxClientError())
                    .andExpect(model().attributeExists("emailError"))
                    .andExpect(view().name("user/joinFailed"));
        }
    }
}
