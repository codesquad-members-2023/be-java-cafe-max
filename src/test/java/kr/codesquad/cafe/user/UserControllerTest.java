package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.domain.Role;
import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {
    private static final String JACK = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    private static final String JERRY_EMAIL = "jerry@email.com";
    private static final String JERRY = "jerry";
    private static final String USER_ID = "userId";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String RECONFIRM_PASSWORD = "reconfirmPassword";
    private static final String PROFILE_EDIT_FORM = "profileEditForm";
    private static final String PROFILE_FORM = "profileForm";
    private static final String JOIN_FORM = "joinForm";
    private static final int NON_EXISTING_USER_ID = 200;
    private static final int NOT_EXIST_PAGE = 2000;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    private MockHttpSession session;
    private User jack;

    @BeforeEach
    void setSession() {
        JoinForm joinForm = new JoinForm(JACK, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
        jack = userService.save(joinForm);
        session = new MockHttpSession();
        session.setAttribute("user", jack);
    }


    @DisplayName("로그인 페이지 테스트")
    @Nested
    class LoginTest {
        @DisplayName("오픈")
        @Test
        void viewLoginPage() throws Exception {
            mockMvc.perform(get("/users/login"))
                    .andExpect(status().isOk());
        }

        @DisplayName("성공")
        @Test
        void loginSuccess() throws Exception {
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, TEST_PASSWORD))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/" + jack.getId()));
        }

        @DisplayName("비밀번호 실패")
        @Test
        void loginFailedByPassword() throws Exception {
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, "12345678").session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("user/login"))
                    .andDo(print());
        }

        @DisplayName("이메일 주소 실패")
        @Test
        void loginFailedByEmail() throws Exception {
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, "jack1@email.com")
                            .param(PASSWORD, "12345ddd").session(session))
                    .andExpect(status().is4xxClientError())
                    .andExpect(view().name("user/loginFailed"));
        }

    }

    @Nested
    @DisplayName("가입 페이지 테스트")
    class JoinTest {
        @DisplayName("오픈")
        @Test
        void viewJoinPage() throws Exception {
            mockMvc.perform(get("/users/join"))
                    .andExpect(model().attributeExists(JOIN_FORM))
                    .andExpect(status().isOk());
        }

        @DisplayName("유저 추가 성공")
        @Test
        void addUserSuccess() throws Exception {
            mockMvc.perform(post("/users")
                            .param(EMAIL, JERRY_EMAIL)
                            .param(NICKNAME, JERRY)
                            .param(PASSWORD, TEST_PASSWORD)
                            .param(RECONFIRM_PASSWORD, TEST_PASSWORD))
                    .andExpect(status().is3xxRedirection())
                    .andDo(print());
            assertThat(userService.findByEmail(JERRY_EMAIL)).isPresent();
        }

        @DisplayName("유저 추가 실패")
        @ParameterizedTest
        @CsvSource({"sss," + JACK + ",a123456789", JACK_EMAIL + ",j,a1223456789", JACK_EMAIL + "," + JACK + ",123456789"})
        void addUserFailed(String email, String nickname, String password) throws Exception {
            mockMvc.perform(post("/users")
                            .param(EMAIL, email)
                            .param(NICKNAME, nickname)
                            .param(PASSWORD, password))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/join"));
        }
    }


    @DisplayName("맴버 리스트 페이지 오픈 테스트")
    @Test
    void viewUsers() throws Exception {
        User target = new User.Builder()
                .id(jack.getId())
                .role(Role.MANAGER)
                .email(jack.getEmail())
                .nickname(jack.getNickname())
                .password(jack.getPassword())
                .build();

        session.setAttribute("user", target);
        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("user/users"));
    }

    @DisplayName("유저 프로필 페이지 테스트")
    @Nested
    class UserProfilePageTest {
        @DisplayName("오픈 성공")
        @Test
        void viewUserSuccess() throws Exception {
            mockMvc.perform(get("/users/" + jack.getId() + "/profile").session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists(USER_ID, PROFILE_FORM))
                    .andExpect(view().name("user/profile"));
        }

        @DisplayName("오픈 실패")
        @Test
        void viewUserFailed() throws Exception {
            mockMvc.perform(get("/users/"+ NOT_EXIST_PAGE)
                            .session(session))
                    .andExpect(status().is4xxClientError());
        }
    }

    @DisplayName("유저 프로필 페이지 업데이트 테스트")
    @Nested
    class ProfileEditPageTest {

        @DisplayName("오픈")
        @Nested
        class OpenTest {
            @DisplayName("성공")
            @Test
            void viewUserProfileSuccess() throws Exception {
                session.setAttribute("user", jack);
                mockMvc.perform(get("/users/" + jack.getId() + "/profile/editForm").session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists(USER_ID, PROFILE_EDIT_FORM))
                        .andExpect(view().name("user/profileEditForm"));
            }

            @DisplayName("실패")
            @Test
            void viewUserProfileFailed() throws Exception {
                mockMvc.perform(get("/users/" + (jack.getId() + 1) + "/profile/editForm").session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }
        }

        @DisplayName("세팅")
        @Nested
        class SettingTest {




            @DisplayName("성공")
            @Test
            void setUserProfileSuccess() throws Exception {
                MockHttpSession session = new MockHttpSession();
                session.setAttribute("user", jack);
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, TEST_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY).session(session))
                        .andExpect(status().is3xxRedirection());

                User changedUser = userService.findById(jack.getId()).orElseThrow();
                assertThat(changedUser.getEmail()).isEqualTo(JERRY_EMAIL);
                assertThat(changedUser.getNickname()).isEqualTo(JERRY);
            }

            @DisplayName("실패(비밀번호 불 일치)")
            @Test
            void setUserProfileFailedByPassword() throws Exception {
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, "987654123a")
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("user/profileEditFormFailed"));
            }

            @DisplayName("실패(유저 아이디)")
            @Test
            void setUserProfileFailedByUserId() throws Exception {
                mockMvc.perform(put("/users/" + NON_EXISTING_USER_ID + "/profile")
                                .param(PASSWORD, TEST_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("실패(형식 오류)")
            @ParameterizedTest
            @CsvSource({JERRY_EMAIL + ",j", JERRY + ",jerry"})
            void setUserProfileFailedByType(String email, String nickname) throws Exception {
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, TEST_PASSWORD)
                                .param(EMAIL, email)
                                .param(NICKNAME, nickname)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().hasErrors())
                        .andExpect(view().name("user/profileEditForm"));
            }
        }
    }


}
