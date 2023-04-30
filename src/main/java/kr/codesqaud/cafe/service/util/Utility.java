package kr.codesqaud.cafe.service.util;

import kr.codesqaud.cafe.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Utility {
    public static String getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null) {
                return loginUser.getUserId();
            }
        }
        return null;
    }

    public static String getUserNameFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser != null) {
                return loginUser.getUserName();
            }
        }
        return null;
    }
}
