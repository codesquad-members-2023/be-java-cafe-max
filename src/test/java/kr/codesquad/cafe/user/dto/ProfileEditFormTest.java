package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileEditFormTest {

    public static final String TEST_NICKNAME = "jack";
    public static final String TEST_EMAIL = "jack@eamil.com";
    public static final String TEST_PASSWORD = "password";
    public static final String TARGET_NICKNAME = "jerry";
    public static final String TARGET_EMAIL = "jerry@eamil.com";

    @DisplayName("PrifileEditForm 정보로 User를 프로필 수정")
    @Test
    void setUser() {
        User user = User.builder()
                .id(1L)
                .nickname(TEST_NICKNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();
        ProfileEditForm profileEditForm = new ProfileEditForm(TARGET_NICKNAME, TARGET_EMAIL, TEST_PASSWORD);

        profileEditForm.setUser(user);

        assertThat(user.getNickname()).isEqualTo(TARGET_NICKNAME);
        assertThat(user.getEmail()).isEqualTo(TARGET_EMAIL);
    }
}
