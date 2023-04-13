package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.dto.member.SignInRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SignInRequestValidator implements Validator {

    private final MemberService memberService;

    public SignInRequestValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SignInRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignInRequest request = (SignInRequest) target;

        if (memberService.isNotSamePassword(request.getEmail(), request.getPassword())) {
            errors.rejectValue("password", "NotMatch");
        }
    }
}
