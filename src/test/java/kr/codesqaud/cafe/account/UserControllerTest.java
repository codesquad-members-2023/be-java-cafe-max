package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class UserControllerTest {
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String JACK_PASSWORD = "123456789a";
    private static final String JACK = "jack";
    private static final String JERRY_EMAIL = "jerry@email.com";
    private static final String JERRY = "jerry";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String NICKNAME = "nickname";
    private static final String USER_ID = "userId";
    private static final String PROFILE_EDIT_FORM = "profileEditForm";
    private static final String PROFILE_FORM = "profileForm";
    private static final String JOIN_FORM = "joinForm";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @DisplayName("로그인 페이지 테스트")
    @Nested
    class LoginTest {
        @DisplayName("열람")
        @Test
        void showLoginPage() throws Exception {
            mockMvc.perform(get("/users/login"))
                    .andExpect(status().isOk());
        }

        @DisplayName("성공")
        @Test
        void loginSuccess() throws Exception {
            int userId = saveAndGetUserJack();
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, JACK_PASSWORD))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/" + userId + "/profile"));
        }

        @DisplayName("비밀번호 실패")
        @Test
        void loginFailedByPassword() throws Exception {
            saveAndGetUserJack();
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, JACK_EMAIL)
                            .param(PASSWORD, "12345678"))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("account/login"));
        }

        @DisplayName("이메일 주소 실패")
        @Test
        void loginFailedByEmail() throws Exception {
            saveAndGetUserJack();
            mockMvc.perform(post("/users/login")
                            .param(EMAIL, "jack1@email.com")
                            .param(PASSWORD, "12345ddd"))
                    .andExpect(status().isOk())
                    .andExpect(model().hasErrors())
                    .andExpect(view().name("account/login"));
        }

    }

    @Nested
    @DisplayName("가입 페이지 테스트")
    class JoinTest {
        @DisplayName("열람")
        @Test
        void showJoinPage() throws Exception {
            mockMvc.perform(get("/users/join"))
                    .andExpect(model().attributeExists(JOIN_FORM))
                    .andExpect(status().isOk());
        }

        @DisplayName("유저 추가 성공")
        @Test
        void addUserSuccess() throws Exception {
            mockMvc.perform(post("/users")
                            .param(EMAIL, JACK_EMAIL)
                            .param(NICKNAME, JACK)
                            .param(PASSWORD, JACK_PASSWORD))
                    .andExpect(status().is3xxRedirection());

            List<User> allMembers = userRepository.getAllUsers();
            System.out.println(allMembers);
            assertThat(userService.findByEmail(JACK_EMAIL)).isPresent();
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
                    .andExpect(view().name("account/join"));
        }
    }


    @DisplayName("맴버 리스트 페이지 열람 테스트")
    @Test
    void showUsers() throws Exception {
        int userId = saveAndGetUserJack();
        MockHttpSession session = new MockHttpSession();
        User user = userRepository.findById((long) userId);
        User target = new User.Builder()
                .id(user.getId())
                .role(Role.MANAGER)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .build();
        session.setAttribute("user", target);
        mockMvc.perform(get("/users").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("account/users"));
    }

    @DisplayName("유저 프로필 페이지 테스트")
    @Nested
    class UserProfilePageTest {
        @DisplayName("열람 성공")
        @Test
        void showUserSuccess() throws Exception {
            int userId = saveAndGetUserJack();
            MockHttpSession session = new MockHttpSession();
            User user = userRepository.findById((long) userId);
            session.setAttribute("user", user);
            mockMvc.perform(get("/users/" + userId + "/profile").session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists(USER_ID, PROFILE_FORM))
                    .andExpect(view().name("account/profile"));
        }

        @DisplayName("열람 실패")
        @Test
        void showUserFailed() throws Exception {
            mockMvc.perform(get("/users/20"))
                    .andExpect(status().is4xxClientError());
        }
    }

    @DisplayName("유저 프로필 수정 페이지 테스트")
    @Nested
    class ProfileEditPageTest {

        @DisplayName("열람")
        @Nested
        class OpenTest {
            @DisplayName("성공")
            @Test
            void showUserProfileSuccess() throws Exception {
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(get("/users/" + userId + "/profile/edit").session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists(USER_ID, PROFILE_EDIT_FORM))
                        .andExpect(view().name("account/profileEditForm"));
            }

            @DisplayName("실패")
            @Test
            void showUserProfileFailed() throws Exception {
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(get("/users/" + (++userId) + "/profile/edit").session(session))
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
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(put("/users/" + userId + "/profile")
                                .param(PASSWORD, JACK_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY).session(session))
                        .andExpect(status().is3xxRedirection());

                User changedUser = userService.findById((long) userId);
                assertThat(changedUser.getEmail()).isEqualTo(JERRY_EMAIL);
                assertThat(changedUser.getNickname()).isEqualTo(JERRY);
            }

            @DisplayName("실패(비밀번호 불 일치)")
            @Test
            void setUserProfileFailedByPassword() throws Exception {
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(put("/users/" + userId + "/profile")
                                .param(PASSWORD, "987654123a")
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().hasErrors())
                        .andExpect(view().name("account/profileEditForm"));
            }

            @DisplayName("실패(유저 아이디)")
            @Test
            void setUserProfileFailedByUserId() throws Exception {
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(put("/users/" + (++userId) + "/profile")
                                .param(PASSWORD, JACK_PASSWORD)
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
                int userId = saveAndGetUserJack();
                MockHttpSession session = new MockHttpSession();
                User user = userRepository.findById((long) userId);
                session.setAttribute("user", user);
                mockMvc.perform(put("/users/" + userId + "/profile")
                                .param(PASSWORD, JACK_PASSWORD)
                                .param(EMAIL, email)
                                .param(NICKNAME, nickname)
                                .session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().hasErrors())
                        .andExpect(view().name("account/profileEditForm"));
            }
        }
    }

    private int saveAndGetUserJack() {
        JoinForm joinForm = new JoinForm(JACK, JACK_EMAIL, JACK_PASSWORD);
        return userService.save(joinForm);
    }

}
