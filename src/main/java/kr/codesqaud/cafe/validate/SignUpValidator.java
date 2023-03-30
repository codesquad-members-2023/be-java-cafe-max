package kr.codesqaud.cafe.validate;

import kr.codesqaud.cafe.dto.SignUpDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpValidator implements Validator {
    private static final String userIdRegex = "^[a-zA-z0-9]{4,20}$";
    private static final String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,32}$";
    private static final String nameRegex = "^[a-zA-Z가-힣][\\sa-zA-Z가-힣]{2,49}$";
    private static final String emailRegex = "^[\\w.%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpDTO dto = (SignUpDTO) target;

        validateUserId(dto.getUserId(), errors);
        validatePassword(dto.getPassword(), dto.getPasswordCheck(), errors);
        validateName(dto.getName(), errors);
        validateEmail(dto.getEmail(), errors);
    }

    private void validateUserId(String userId, Errors errors) {
        if(userId == null || userId.isBlank()) {
            errors.rejectValue("userId", "required.userId");
            return;
        }

        matcher = Pattern.compile(userIdRegex).matcher(userId);
        if (!matcher.find()) {
            errors.rejectValue("userId", "invalidPolicy.userId");
        }
    }

    private void validatePassword(String password, String passwordCheck, Errors errors) {
        if(password == null || password.isBlank()) {
            errors.rejectValue("password", "required.password");
            return;
        }

        matcher = Pattern.compile(passwordRegex).matcher(password);
        if (!matcher.find()) {
            errors.rejectValue("password", "invalidPolicy.password");
        }

        if (!password.equals(passwordCheck)) {
            errors.rejectValue("passwordCheck", "invalidDoubleCheck.password");
        }
    }

    private void validateName(String name, Errors errors) {
        if(name == null || name.isBlank()) {
            errors.rejectValue("name", "required.name");
            return;
        }

        matcher = Pattern.compile(nameRegex).matcher(name);
        if (!matcher.find()) {
            errors.rejectValue("name", "invalidPolicy.name");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if(email == null || email.isBlank()) {
            errors.rejectValue("email", "required.email");
            return;
        }

        matcher = Pattern.compile(emailRegex).matcher(email);
        if (!matcher.find()) {
            errors.rejectValue("email", "invalidPolicy.email");
        }
    }
}
