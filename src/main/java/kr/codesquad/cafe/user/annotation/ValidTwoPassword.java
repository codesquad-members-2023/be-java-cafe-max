package kr.codesquad.cafe.user.annotation;

import kr.codesquad.cafe.user.JoinFormValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = JoinFormValidator.class)
public @interface ValidTwoPassword {

    String message() default "{error.password.missMatch}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
