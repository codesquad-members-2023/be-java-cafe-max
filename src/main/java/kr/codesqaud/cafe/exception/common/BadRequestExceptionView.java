package kr.codesqaud.cafe.exception.common;

import java.util.Arrays;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;

public enum BadRequestExceptionView {

    SIGN_IN_FORM(SignInRequest.class, "authentication/signIn"),
    SIGN_UP_FORM(SignUpRequest.class, "member/signUp"),
    PROFILE_EDIT_FORM(ProfileEditRequest.class, "member/profileEdit"),
    EMPTY(Object.class, "error/500");

    private final Class<?> cls;
    private final String viewName;

    BadRequestExceptionView(Class<?> cls, String viewName) {
        this.cls = cls;
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public static BadRequestExceptionView getBadRequestExceptionView(Class<?> cls) {
        return Arrays.stream(values())
            .filter(type -> type.cls.isAssignableFrom(cls))
            .findAny()
            .orElse(EMPTY);
    }
}
