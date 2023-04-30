package kr.codesquad.cafe.user.dto;

import kr.codesquad.cafe.user.domain.User;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class JoinFormTest {

    public static final String TEST_PASSWORD = "password";
    public static final String TEST_EMAIL = "jack@email.com";
    public static final String TEST_NAME = "jack";
    public static final String WRONG_PASSWORD = "password2";

    @DisplayName("encrypt 받고 정보를 User객체에 담는다")
    @Test
    void toUser() {
        StringEncryptor encryptor = mock(StringEncryptor.class);
        given(encryptor.encrypt(TEST_PASSWORD)).willReturn(TEST_PASSWORD);
        JoinForm joinForm = new JoinForm(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD);

        User user = joinForm.toUser(encryptor);

        assertThat(user.getNickname()).isEqualTo(TEST_NAME);
        assertThat(user.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(user.getPassword()).isEqualTo(TEST_PASSWORD);
    }

    @DisplayName("입력한 두 비번이 일치하는지 확인한다")
    @Test
    void isSamePassword() {
        JoinForm joinForm1 = new JoinForm(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD);

        boolean result1 = joinForm1.isSamePassword();

        assertThat(result1).isTrue();

        JoinForm joinForm2 = new JoinForm(TEST_NAME, TEST_EMAIL, TEST_PASSWORD, WRONG_PASSWORD);

        boolean result2 = joinForm2.isSamePassword();

        assertThat(result2).isFalse();
    }
}
