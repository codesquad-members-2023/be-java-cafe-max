package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserFormTest {
    private static final String TEST_EMAIL = "jack@eamil.com";
    private static final String TEST_NICKNAME = "jack";
    private static final long TEST_ID = 1L;

    @DisplayName("유저 정보에서 닉네임,이메일,아이디만 가져온다")
    @Test
    void from() {
        User user = User.builder()
                .id(TEST_ID)
                .email(TEST_EMAIL)
                .nickname(TEST_NICKNAME)
                .build();

        UserForm userForm = UserForm.from(user);

        assertThat(userForm.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(userForm.getNickname()).isEqualTo(TEST_NICKNAME);
        assertThat(userForm.getId()).isEqualTo(TEST_ID);
        assertThat(userForm).hasOnlyFields("email", "nickname", "id");
    }
}
