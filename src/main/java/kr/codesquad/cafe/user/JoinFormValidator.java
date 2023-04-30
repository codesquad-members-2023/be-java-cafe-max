package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.annotation.ValidTwoPassword;
import kr.codesquad.cafe.user.dto.JoinForm;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class JoinFormValidator implements ConstraintValidator<ValidTwoPassword, JoinForm> {

    @Override
    public void initialize(ValidTwoPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(JoinForm joinForm, ConstraintValidatorContext context) {
        return joinForm.isSamePassword();
    }
}
