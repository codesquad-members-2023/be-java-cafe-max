package kr.codesqaud.cafe.common.auth.utill;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@Component
public class AuthSessionValidator {
	private AuthSessionValidator() {

	}

	public static void validatePageAnyoneCanAccess(HttpSession session) throws NoAuthSessionException {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		if (authSession == null) {
			throw new NoAuthSessionException();
		}
	}
}
