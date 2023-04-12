package kr.codesqaud.cafe.account;

import kr.codesqaud.cafe.account.dto.JoinForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class JoinFormValidator implements Validator {

    private static final String EMAIL = "email";
    private final UserService userService;

    public JoinFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinForm joinForm = (JoinForm) target;
        if (errors.hasErrors()) {
            return;
        }
        if (userService.containsEmail(joinForm.getEmail())) {
            errors.rejectValue(EMAIL, "error.email.duplicate");
            return;
        }
        if (!joinForm.getPassword().equals(joinForm.getReconfirmPassword())) {
            errors.rejectValue("reconfirmPassword", "error.password.missMatch");
        }
    }
}
