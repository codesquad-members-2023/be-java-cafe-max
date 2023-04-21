package kr.codesqaud.cafe.user.service;

import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;


@Service
public class LoginService {

    public Boolean validationRequestPassword(String repositoryPassword, String requestPassword) throws AccessDeniedException {
        if(repositoryPassword.equals(requestPassword)){
            return true;
        };
        throw new AccessDeniedException("틀린 패스워드");
    }
}
