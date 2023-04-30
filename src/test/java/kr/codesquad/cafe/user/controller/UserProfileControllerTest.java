package kr.codesquad.cafe.user.controller;

import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.global.auth.AuthBeforeAdvice;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.user.UserService;
import kr.codesquad.cafe.user.domain.User;
import kr.codesquad.cafe.user.dto.JoinForm;
import kr.codesquad.cafe.user.exception.DuplicateEmailException;
import kr.codesquad.cafe.user.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
@Import({AopAutoConfiguration.class, AuthBeforeAdvice.class})
public class UserProfileControllerTest {
    private static final String JACK_NICKNAME = "jack";
    private static final String JACK_EMAIL = "jack@email.com";
    private static final String TEST_PASSWORD = "123456789a";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String ATTR_NAME_USER = "user";
    private static final String JERRY_EMAIL = "jerry@email.com";
    private static final String JERRY = "jerry";
    private static final String USER_ID = "userId";
    private static final String PROFILE_EDIT_FORM = "profileEditForm";
    private static final String PROFILE_FORM = "profileForm";
    private static final int NOT_MATCH_USER_ID = 200;
    private static final int NOT_EXIST_PAGE = 2000;
    public static final int OTHER_USER_ID = 20;
    public static final String NO_MATCH_PASSWORD = "987654123a";
    public static final int CURRENT_PAGE = 4;
    public static final int TOTAL_PAGES = 10;
    public static final String PAGE = "page";
    public static final String PAGE_NUMBER = "1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    private MockHttpSession session;
    private User jack;


    private static User setJack() {
        return new User.Builder()
                .id(1L)
                .email(JACK_EMAIL)
                .nickname(JACK_NICKNAME)
                .email(JACK_EMAIL)
                .password(TEST_PASSWORD)
                .build();
    }


    @BeforeEach
    void setSession() {
        JoinForm joinForm = new JoinForm(JACK_NICKNAME, JACK_EMAIL, TEST_PASSWORD, TEST_PASSWORD);
        jack = setJack();
        when(userService.save(joinForm)).thenReturn(jack);
        session = new MockHttpSession();
        session.setAttribute(ATTR_NAME_USER, jack);
    }

    @DisplayName("유저 프로필 폼")
    @Nested
    class UserProfilePageTest {
        @DisplayName("유저 본인은 접근 가능")
        @Test
        void viewUserSuccess() throws Exception {
            mockMvc.perform(get("/users/" + jack.getId() + "/profile").session(session))
                    .andExpect(status().isOk())
                    .andExpect(model().attributeExists(USER_ID, PROFILE_FORM))
                    .andExpect(view().name("user/profile"));
        }

        @DisplayName("유저 본인이 아니면 접근 불가하고 에러 페이지를 이동한다")
        @Test
        void viewUserFailed() throws Exception {
            mockMvc.perform(get("/users/" + NOT_EXIST_PAGE + "/profile")
                            .session(session))
                    .andExpect(status().is4xxClientError());
        }
    }

    @DisplayName("유저 프로필 수정 페이지")
    @Nested
    class ProfileEditPageTest {

        @DisplayName("접근")
        @Nested
        class OpenTest {
            @DisplayName("유저 본인이면 접근 가능하고 프로필 수정 페이지로 이동한다")
            @Test
            void viewUserProfileSuccess() throws Exception {
                session.setAttribute(ATTR_NAME_USER, jack);
                mockMvc.perform(get("/users/" + jack.getId() + "/profile/editForm").session(session))
                        .andExpect(status().isOk())
                        .andExpect(model().attributeExists(USER_ID, PROFILE_EDIT_FORM))
                        .andExpect(view().name("user/profileEditForm"));
            }

            @DisplayName("유저 본인이 아니면 접근 불가하고 에러 페이지로 이동한다")
            @Test
            void viewUserProfileFailed() throws Exception {
                mockMvc.perform(get("/users/" + OTHER_USER_ID + "/profile/editForm").session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }
        }

        @DisplayName("세팅")
        @Nested
        class SettingTest {

            @DisplayName("성공하면 다시 유저 프로필 페이지로 Redirect 한다")
            @Test
            void setUserProfileSuccess() throws Exception {
                MockHttpSession session = new MockHttpSession();
                session.setAttribute(ATTR_NAME_USER, jack);
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, TEST_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY).session(session))
                        .andExpect(status().is3xxRedirection())
                        .andExpect(redirectedUrl("/users/" + jack.getId() + "/profile"));
            }

            @DisplayName("일력한 비밀번호가 일치하지 않는 에러가 발생시 오류 메시지와 함께 프로필 수정페이지와 이동")
            @Test
            void setUserProfileFailedByPassword() throws Exception {
                doThrow(new InvalidPasswordException()).when(userService).checkEditInfo(any(), any());
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, NO_MATCH_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("user/profileEditFormFailed"));
            }

            @DisplayName("중복된 이메일 에러 발생시 오류 메시지와 함께 프로필 수정페이지와 이동")
            @Test
            void setUserProfileFailedByEmail() throws Exception {
                doThrow(new DuplicateEmailException()).when(userService).checkEditInfo(any(), any());
                mockMvc.perform(put("/users/" + jack.getId() + "/profile")
                                .param(PASSWORD, NO_MATCH_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("user/profileEditFormFailed"));
            }

            @DisplayName("유저 본인 profile에 대한 수정 요청이 아니면 에러 페이지로 이동한다")
            @Test
            void setUserProfileFailedByUserId() throws Exception {
                mockMvc.perform(put("/users/" + NOT_MATCH_USER_ID + "/profile")
                                .param(PASSWORD, TEST_PASSWORD)
                                .param(EMAIL, JERRY_EMAIL)
                                .param(NICKNAME, JERRY)
                                .session(session))
                        .andExpect(status().is4xxClientError())
                        .andExpect(view().name("error/4xx"));
            }

            @DisplayName("수정 요청 형식 오류는 에러와 함께 유저 프로필페이지로 이동한다")
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

    @DisplayName("유저 info 페이지")
    @Nested
    class UserInfoPage {

        @DisplayName("Page Param이 없을 때 성공적으로 접근가능하면 페이지 info와 작성한 Posts 심플 form 및 프로필 정보를 담아서 보여준다")
        @Test
        void viewUserInfoSuccess() throws Exception {
            given(postService.getAllSimplePostFormByUser(anyLong(), anyInt())).willReturn(new ArrayList<>());
            PagesInfo pagesInfo = PagesInfo.of(CURRENT_PAGE, TOTAL_PAGES);
            given(postService.getPagesInfoByUser(anyInt(), anyLong())).willReturn(pagesInfo);
            mockMvc.perform(get("/users/" + jack.getId())
                            .session(session))
                    .andExpect(model().attributeExists("profileForm", "simpleForms", "pagesInfo"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/info"));
        }

        @DisplayName("Page Param이 았을 때 성공적으로 접근가능하면 페이지 info와 작성한 Posts 심플 form 및 프로필 정보를 담아서 보여준다")
        @Test
        void viewUserInfoSuccessWithPageParam() throws Exception {
            given(postService.getAllSimplePostFormByUser(anyLong(), anyInt())).willReturn(new ArrayList<>());
            PagesInfo pagesInfo = PagesInfo.of(CURRENT_PAGE, TOTAL_PAGES);
            given(postService.getPagesInfoByUser(anyInt(), anyLong())).willReturn(pagesInfo);
            mockMvc.perform(get("/users/" + jack.getId())
                            .param(PAGE, PAGE_NUMBER)
                            .session(session))
                    .andExpect(model().attributeExists("profileForm", "simpleForms", "pagesInfo"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/info :: #postsPage"));
        }


        @DisplayName("유저 가 아닐 때 에러 페이지를 보여준다.")
        @Test
        void viewUserInfoFailedByNotUser() throws Exception {
            mockMvc.perform(get("/users/" + jack.getId()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/users/login"));
        }

        @DisplayName("접근 할 수 없은 에러가 발생시 에러 페이지를 보여준다.")
        @Test
        void viewUserInfoFailedByError() throws Exception {
            mockMvc.perform(get("/users/" + NOT_MATCH_USER_ID).session(session))
                    .andExpect(status().isBadRequest())
                    .andExpect(view().name("error/4xx"));
        }
    }

}
