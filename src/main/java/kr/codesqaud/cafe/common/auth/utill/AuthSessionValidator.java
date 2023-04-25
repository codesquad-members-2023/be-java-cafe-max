package kr.codesqaud.cafe.common.auth.utill;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.common.auth.exception.NoAccessPermissionException;
import kr.codesqaud.cafe.common.auth.exception.NoAuthSessionException;
import kr.codesqaud.cafe.user.controller.response.AuthSession;

@Component
public class AuthSessionValidator {
	private AuthSessionValidator() {

	}

	/**
	 * 현재 접속자가 로그인 된 상태인지 확인.
	 * @param session 세션
	 * @return 로그인 된 상태이면 true 반환
	 */
	public static boolean isSignedIn(HttpSession session) {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		return authSession != null;
	}

	/**
	 * 권한 검증: 로그인한 모든 유저가 접속 가능한 페이지.
	 * @param session 세션
	 * @return 로그인 검증에 성공한 경우 로그인된 유저의 id를 반환
	 * @throws NoAuthSessionException 로그인 하지 않은 상태인 경우 예외 발생
	 */
	public static long validateUserIsSignedIn(HttpSession session) throws NoAuthSessionException {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		if (authSession == null) {
			throw new NoAuthSessionException();
		}
		return authSession.getId();
	}

	/**
	 * 권한 검증: 게시글 작성자만 접근 가능한 페이지.
	 * @param session 세션
	 * @param writer_id 게시글 작성자
	 * @throws NoAccessPermissionException 현재 로그인한 유저가 게시글 작성자가 아닌 경우 예외 발생
	 */
	public static void validatePageOnlyWriterCanAccess(HttpSession session, long writer_id) throws
		NoAccessPermissionException {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		if (authSession == null || authSession.getId() != writer_id) {
			throw new NoAccessPermissionException();
		}
	}

	/**
	 * 권한 검증: admin만 접속 가능한 페이지.
	 * @param session 세션
	 * @throws NoAccessPermissionException admin이 아닌 경우 예외 발생
	 */
	public static void validateIsAdmin(HttpSession session) throws NoAccessPermissionException {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		if (authSession == null || authSession.getId() != 1) {
			throw new NoAccessPermissionException();
		}
	}

	/**
	 * 권한 검증: 개인만 접속 가능한 private 페이지.
	 * @param session 세션
	 * @param id 접속하려는 private 페이지의 유저 id
	 * @throws NoAccessPermissionException 현재 유저와 접속하려는 private 페이지의 유저가 일치하지 않으면 예외 발생
	 */
	public static void validateIsPrivatePage(HttpSession session, long id) throws NoAccessPermissionException {
		AuthSession authSession = (AuthSession)session.getAttribute("authSession");
		if (authSession == null || authSession.getId() != id) {
			throw new NoAccessPermissionException();
		}
	}
}
