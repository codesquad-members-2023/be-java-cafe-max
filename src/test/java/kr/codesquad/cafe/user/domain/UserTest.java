package kr.codesquad.cafe.user.domain;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kr.codesquad.cafe.user.domain.Role.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class UserTest {

    private static final String TEST_PASSWORD = "password";
    private static final String TEST_NICKNAME = "jack";
    private static final String TEST_EMAIL = "jack@email.com";
    private static final long TEST_ID = 1L;
    private static final String WRONG_PASSWORD = "wrongPassword";
    private static final String WRONG_EMAIL = "wrongEmail";
    public static final long NO_MATCH_ID = 2L;
    public static final String TARGET_EMAIL = "jerry@email.com";
    public static final String TARGET_NICKNAME = "jerry";

    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(TEST_ID)
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .build();
    }

    @DisplayName("유저의 비번이 일치하면 ture, 아니면 false를 return한다")
    @Test
    void isSamePassword() {
        StringEncryptor encryptor = mock(StringEncryptor.class);
        given(encryptor.decrypt(TEST_PASSWORD)).willReturn(TEST_PASSWORD);


        assertThat(user.isSamePassword(encryptor, TEST_PASSWORD)).isTrue();
        assertThat(user.isSamePassword(encryptor, WRONG_PASSWORD)).isFalse();
    }

    @DisplayName("유저의 이메일이 일치히면 true, 아니면 false를 return한다")
    @Test
    void isSameEmail() {
        assertThat(user.isSameEmail(TEST_EMAIL)).isTrue();
        assertThat(user.isSameEmail(WRONG_EMAIL)).isFalse();
    }

    @DisplayName("유자의 아이디가 일치히면 true, 아니면 false를 return한다")
    @Test
    void isSameId() {
        assertThat(user.isSameId(TEST_ID)).isTrue();
        assertThat(user.isSameId(NO_MATCH_ID)).isFalse();
    }

    @DisplayName("유자가 메니저이면 true, 아니면 false를 return한다")
    @Test
    void isManager() {
        User manager = User.builder()
                .id(TEST_ID)
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .password(TEST_PASSWORD)
                .role(MANAGER)
                .build();
        assertThat(manager.isManager()).isTrue();
        assertThat(user.isManager()).isFalse();
    }

    @DisplayName("프로필 닉네임 및 이메일 설정")
    @Test
    void setProfile() {
        user.setProfile(TARGET_NICKNAME, TARGET_EMAIL);
        assertThat(user.getNickname()).isEqualTo(TARGET_NICKNAME);
        assertThat(user.getEmail()).isEqualTo(TARGET_EMAIL);
    }
}
