package kr.codesqaud.cafe.utils;

import kr.codesqaud.cafe.user.domain.User;

import javax.servlet.http.HttpSession;

public class Session {

    private static final String sessionName = "sessionUser";


    public static void logIn(HttpSession session, User user){
        session.setAttribute(sessionName, user);
    }

    public static boolean isLoggedIn(HttpSession session){
        return session.getAttribute(sessionName) != null;
    }


    public static String getUserName(HttpSession session) {
        User user = (User) session.getAttribute(sessionName);
        return user.getName();
    }
}
