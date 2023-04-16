package kr.codesqaud.cafe.account.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotEmpty
@Email
@Target({FIELD})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface ValidEmail {

    String message() default "{error.email.correct}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
