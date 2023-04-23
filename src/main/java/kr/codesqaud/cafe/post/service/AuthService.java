package kr.codesqaud.cafe.post.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;

@Service
public class AuthService {

    public boolean checkLogin(HttpSession session) throws AccessDeniedException {
        if(session.getAttribute("sessionId") == null) {
            throw new AccessDeniedException("로그인 해주세요");
        }
        return true;
    }

}
