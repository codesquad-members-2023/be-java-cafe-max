package kr.codesqaud.cafe.account.service.validator;

import static kr.codesqaud.cafe.utils.FiledName.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kr.codesqaud.cafe.account.service.UserService;
import kr.codesqaud.cafe.account.controller.form.JoinForm;

@Component
public class JoinFormValidator implements Validator {

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
		JoinForm joinForm = (JoinForm)target;
		if (userService.containEmail(joinForm.getEmail())) {
			errors.rejectValue(EMAIL, "error.email.duplicate");
		}
	}
}
