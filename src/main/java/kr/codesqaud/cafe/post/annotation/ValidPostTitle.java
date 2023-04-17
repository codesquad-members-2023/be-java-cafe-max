package kr.codesqaud.cafe.post.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@NotEmpty
@Size(max = 64, min = 2, message = "{error.title.size}")
@Target({FIELD})
@ReportAsSingleViolation
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface ValidPostTitle {

    String message() default "{error.title.size}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
