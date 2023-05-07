package kr.codesquad.cafe.user.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Email
@Target({FIELD})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidEmail {

    String message() default "{error.email.correct}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
