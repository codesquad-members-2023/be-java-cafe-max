package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SignUpRequestValidator implements Validator {

    private final MemberService memberService;

    public SignUpRequestValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest request = (SignUpRequest) target;

        if (memberService.isDuplicateEmail(request.getEmail())) {
            errors.rejectValue("email", "Duplicate");
        }
    }
}
