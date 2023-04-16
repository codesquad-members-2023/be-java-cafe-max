package kr.codesqaud.cafe.validator;

import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfileEditRequestValidator implements Validator {

    private final MemberService memberService;

    public ProfileEditRequestValidator(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProfileEditRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileEditRequest request  = (ProfileEditRequest) target;

        if (memberService.isDuplicateEmailAndId(request.getEmail(), request.getId())) {
            errors.rejectValue("email", "Duplicate");
        }

        if (memberService.isNotSamePassword(request.getEmail(), request.getPassword())) {
            errors.rejectValue("password", "NotMatch");
        }
    }
}
