package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.config.session.SignIn;
import kr.codesqaud.cafe.dto.member.SignInRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.util.SignInSessionUtil;
import kr.codesqaud.cafe.validator.ProfileEditRequestValidator;
import kr.codesqaud.cafe.validator.SignInRequestValidator;
import kr.codesqaud.cafe.validator.SignUpRequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
public class MemberController {

    private final MemberService memberService;
    private final SignInRequestValidator signInRequestValidator;
    private final SignUpRequestValidator signUpRequestValidator;
    private final ProfileEditRequestValidator profileEditRequestValidator;

    public MemberController(MemberService memberService,
        SignInRequestValidator signInRequestValidator,
        SignUpRequestValidator signUpRequestValidator,
        ProfileEditRequestValidator profileEditRequestValidator) {
        this.memberService = memberService;
        this.signInRequestValidator = signInRequestValidator;
        this.signUpRequestValidator = signUpRequestValidator;
        this.profileEditRequestValidator = profileEditRequestValidator;
    }

    @InitBinder("signUpRequest")
    public void initSignUpRequest(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpRequestValidator);
    }

    @InitBinder("signInRequest")
    public void initSignInRequest(WebDataBinder dataBinder) {
        dataBinder.addValidators(signInRequestValidator);
    }

    @InitBinder("profileEditRequest")
    public void initProfileEditRequest(WebDataBinder dataBinder) {
        dataBinder.addValidators(profileEditRequestValidator);
    }

    @GetMapping("/members")
    public String findAll(Model model) {
        model.addAttribute("memberResponses", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/members/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("memberResponse", memberService.findById(id));
        return "member/profile";
    }

    @GetMapping("/members/{id}/profile")
    public String profileEditForm(@PathVariable Long id, Model model,
        @SignIn AccountSession accountSession) {
        if (!id.equals(accountSession.getId())) {
            throw new UnauthorizedException();
        }

        model.addAttribute("profileEditRequest", ProfileEditRequest.from(memberService.findById(id)));
        return "member/profileEdit";
    }

    @PutMapping("/members/{id}")
    public String editProfile(@PathVariable Long id, @Valid ProfileEditRequest profileEditRequest,
        BindingResult bindingResult, @SignIn AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "member/profileEdit";
        }

        profileEditRequest.setId(id);
        memberService.update(profileEditRequest, accountSession.getId());
        return "redirect:/members/{id}";
    }

    @GetMapping("/members/sign-up")
    public String signUpForm(SignUpRequest signUpRequest) {
        return "member/signUp";
    }

    @PostMapping("/members/sign-up")
    public String signUp(@Valid SignUpRequest signUpRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        memberService.signUp(signUpRequest);
        return "redirect:/";
    }

    @GetMapping("/members/sign-in")
    public String signInForm(SignInRequest signInRequest) {
        return "member/signIn";
    }

    @PostMapping("/members/sign-in")
    public String signIn(@Valid SignInRequest signInRequest, BindingResult bindingResult,
        HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "member/signIn";
        }

        SignInSessionUtil.create(httpSession, memberService.createSession(signInRequest.getEmail()));
        return "redirect:/";
    }

    @PostMapping("/members/sign-out")
    public String signOut(HttpSession httpSession) {
        SignInSessionUtil.invalidate(httpSession);
        return "redirect:/";
    }
}
