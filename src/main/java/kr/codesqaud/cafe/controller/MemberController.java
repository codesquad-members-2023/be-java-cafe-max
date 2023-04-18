package kr.codesqaud.cafe.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kr.codesqaud.cafe.config.session.AccountSession;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignInRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.exception.common.UnauthorizedException;
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
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
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

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("memberResponses", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("memberResponse", memberService.findById(id));
        return "member/profile";
    }

    @GetMapping("/{id}/form")
    public String profileEditForm(@PathVariable Long id, Model model,
        @RequestAttribute AccountSession accountSession) {
        memberService.validateUnauthorized(id, accountSession.getId());
        model.addAttribute("profileEditRequest", ProfileEditRequest.from(memberService.findById(id)));
        return "member/profileEdit";
    }

    @PutMapping("/{id}")
    public String editProfile(@PathVariable Long id, @Valid ProfileEditRequest profileEditRequest,
        BindingResult bindingResult, @RequestAttribute AccountSession accountSession) {
        if (bindingResult.hasErrors()) {
            return "member/profileEdit";
        }

        profileEditRequest.setId(id);
        memberService.update(profileEditRequest, accountSession.getId());
        return "redirect:/members/{id}";
    }

    @GetMapping("/sign-up")
    public String signUpForm(SignUpRequest signUpRequest) {
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
    public String signInForm(SignInRequest signInRequest) {
        return "member/signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid SignInRequest signInRequest, BindingResult bindingResult,
        HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "member/signIn";
        }

        AccountSession accountSession = new AccountSession(
            memberService.findByEmail(signInRequest.getEmail()).getId());
        SignInSessionUtil.create(httpSession, accountSession);
        return "redirect:/";
    }

    @PostMapping("/sign-out")
    public String signOut(HttpSession httpSession) {
        SignInSessionUtil.invalidate(httpSession);
        return "redirect:/";
    }
}
