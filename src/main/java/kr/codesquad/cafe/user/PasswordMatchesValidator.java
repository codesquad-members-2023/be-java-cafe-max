package kr.codesquad.cafe.user;

import kr.codesquad.cafe.user.annotation.PasswordMatches;
import kr.codesquad.cafe.user.dto.JoinForm;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, JoinForm> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(JoinForm joinForm, ConstraintValidatorContext context) {
        return joinForm.isSamePassword();
    }
}
