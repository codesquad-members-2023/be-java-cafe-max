package kr.codesqaud.cafe.account.validator;

import static kr.codesqaud.cafe.utils.FiledName.*;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.codesqaud.cafe.account.User;
import kr.codesqaud.cafe.account.UserRepository;
import kr.codesqaud.cafe.account.form.LoginForm;

@Component
public class LoginFormValidator implements Validator {

	private final UserRepository userRepository;

	public LoginFormValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoginForm loginForm = (LoginForm)target;
		Optional<User> userOptional = userRepository.findByEmail(loginForm.getEmail());
		if (userOptional.isEmpty()) {
			errors.rejectValue(EMAIL, "error.email.notExist");
			return;
		}
		User user = userOptional.get();
		if (!user.getPassword().equals(loginForm.getPassword())) {
			errors.rejectValue(PASSWORD, "error.password.notMatch");
		}
	}
}
