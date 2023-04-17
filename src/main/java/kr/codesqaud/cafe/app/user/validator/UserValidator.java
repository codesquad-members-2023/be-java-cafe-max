package kr.codesqaud.cafe.app.user.validator;

import java.util.Objects;
import kr.codesqaud.cafe.errors.exception.RestApiException;
import kr.codesqaud.cafe.errors.errorcode.UserErrorCode;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    // 두 회원의 비밀번호가 일치하는지 검증
    public void validateEqualConfirmPassword(String requestPassword, String password) {
        if (!isPasswordMatching(requestPassword, password)) {
            throw new RestApiException(UserErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    // 로그인 검증 (아이디 존재, 비밀번호 일치)
    public void validateLoginPassword(String requestPassword, String password) {
        if (!isPasswordMatching(requestPassword, password)) {
            throw new RestApiException(UserErrorCode.NOT_MATCH_LOGIN);
        }
    }

    // 비밀번호가 일치하는지 않는지 검사
    private boolean isPasswordMatching(String requestPassword, String password) {
        return !Objects.equals(requestPassword, password);
    }

    // 수정하고자 하는 이메일이 기존 이메일과 동일한지 검사
    public boolean isEmailUnChanged(String existingEmail, String newEmail) {
        return Objects.equals(existingEmail, newEmail);
    }
}
