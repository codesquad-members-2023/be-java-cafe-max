package kr.codesqaud.cafe.post.service;

import kr.codesqaud.cafe.post.domain.Post;
import kr.codesqaud.cafe.user.domain.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    public boolean checkLogin(HttpSession session){
        if(session.getAttribute("sessionUser") == null) {
            return false;
        }
        return true;
    }

    public boolean checkAuth(HttpSession session, Post post){
        User user = (User) session.getAttribute("sessionUser");
        return user.getName().equals(post.getWriter());
    }
}
