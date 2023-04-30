package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.authentication.AccountResponse;
import kr.codesqaud.cafe.dto.authentication.SignInRequest;
import kr.codesqaud.cafe.service.AuthenticationService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/sign-in")
    public String showSignInForm(SignInRequest signInRequest) {
        return "authentication/signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid SignInRequest signInRequest, BindingResult bindingResult,
        HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "authentication/signIn";
        }

        AccountResponse accountResponse = authenticationService.signIn(signInRequest);
        SignInSessionUtil.create(httpSession, new AccountSession(accountResponse.getMemberId(), accountResponse.getMemberNickname()));
        return "redirect:/";
    }

    @PostMapping("/sign-out")
    public String signOut(HttpSession httpSession) {
        SignInSessionUtil.invalidate(httpSession);
        return "redirect:/";
    }
}
