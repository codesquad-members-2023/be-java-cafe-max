package kr.codesqaud.cafe.utils;

import org.springframework.mock.web.MockHttpSession;

import kr.codesqaud.cafe.global.config.Session;

public class SessionTestUtils {

	public static MockHttpSession createMockHttpSession() {
		MockHttpSession httpSession = new MockHttpSession();
		Session session = new Session("id", "testUser");
		httpSession.setAttribute(Session.LOGIN_USER, session);
		return httpSession;
	}
}
