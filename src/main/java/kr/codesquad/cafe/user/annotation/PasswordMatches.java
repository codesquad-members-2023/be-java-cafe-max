package kr.codesquad.cafe.user.annotation;

import kr.codesquad.cafe.user.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {

    String message() default "{error.password.missMatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
