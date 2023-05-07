package kr.codesquad.cafe.user.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotBlank
@Size(max = 64, min = 2, message = "{error.nickname.size}")
@Target({FIELD})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidNickName {

    String message() default "{error.nickname.size}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
