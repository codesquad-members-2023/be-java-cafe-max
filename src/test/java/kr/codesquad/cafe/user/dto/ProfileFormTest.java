package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileFormTest {

    public static final String TEST_NICKNAME = "jack";
    private static final String TEST_EMAIL = "jack@eamil.com";

    @DisplayName("유저 정보에서 닉네임과 이메일만 가져온다")
    @Test
    void from() {
        User user = User.builder()
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .build();

        ProfileForm profileForm = ProfileForm.from(user);

        assertThat(profileForm.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(profileForm.getNickname()).isEqualTo(TEST_NICKNAME);
        assertThat(profileForm).hasOnlyFields("email", "nickname");
    }
}
