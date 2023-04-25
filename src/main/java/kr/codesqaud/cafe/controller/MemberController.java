package kr.codesqaud.cafe.controller;

import static kr.codesqaud.cafe.util.SignInSessionUtil.SIGN_IN_SESSION_NAME;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.member.MemberResponse;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignInRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String showMembers(Model model) {
        model.addAttribute("memberResponses", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        model.addAttribute("memberResponse", memberService.findById(id));
        return "member/profile";
    }

    @GetMapping("/{id}/form")
    public String showProfileEditForm(@PathVariable Long id, Model model,
        @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        model.addAttribute("profileEditRequest", memberService.findProfileForEditing(id, accountSession.getId()));
        return "member/profileEdit";
    }

    @PutMapping("/{id}")
    public String editProfile(@PathVariable Long id, @Valid ProfileEditRequest profileEditRequest,
        BindingResult bindingResult, @SessionAttribute(SIGN_IN_SESSION_NAME) AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "member/profileEdit";
        }

        memberService.update(profileEditRequest, accountSession.getId());
        return "redirect:/members/{id}";
    }

    @GetMapping("/sign-up")
    public String showSignUpForm(SignUpRequest signUpRequest) {
        return "member/signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid SignUpRequest signUpRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        memberService.signUp(signUpRequest);
        return "redirect:/";
    }

    @GetMapping("/sign-in")
    public String showSignInForm(SignInRequest signInRequest) {
        return "member/signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid SignInRequest signInRequest, BindingResult bindingResult,
        HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "member/signIn";
        }

        MemberResponse memberResponse = memberService.signIn(signInRequest);
        SignInSessionUtil.create(httpSession, new AccountSession(memberResponse.getId(), memberResponse.getNickname()));
        return "redirect:/";
    }

    @PostMapping("/sign-out")
    public String signOut(HttpSession httpSession) {
        SignInSessionUtil.invalidate(httpSession);
        return "redirect:/";
    }
}
