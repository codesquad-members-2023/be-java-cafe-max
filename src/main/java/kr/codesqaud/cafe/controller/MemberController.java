package kr.codesqaud.cafe.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.validator.ProfileEditRequestValidator;
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
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;
    private final SignUpRequestValidator signUpRequestValidator;
    private final ProfileEditRequestValidator profileEditRequestValidator;

    public MemberController(MemberService memberService,
        SignUpRequestValidator signUpRequestValidator,
        ProfileEditRequestValidator profileEditRequestValidator) {
        this.memberService = memberService;
        this.signUpRequestValidator = signUpRequestValidator;
        this.profileEditRequestValidator = profileEditRequestValidator;
    }

    @InitBinder("signUpRequest")
    public void initSignUpRequest(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpRequestValidator);
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

    @PostMapping
    public String signUp(@Valid SignUpRequest signUpRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        memberService.signUp(signUpRequest);
        return "redirect:/members";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("memberResponse", memberService.findById(id));
        return "member/profile";
    }

    @PutMapping("/{id}")
    public String editProfile(@Valid ProfileEditRequest profileEditRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/profileEdit";
        }

        memberService.update(profileEditRequest);
        return "redirect:/members/{id}";
    }

    @GetMapping("/{id}/edit")
    public String profileEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("profileEditRequest", ProfileEditRequest.from(memberService.findById(id)));
        return "member/profileEdit";
    }

    @GetMapping("/sign-up")
    public String signUpForm(SignUpRequest signUpRequest) {
        return "member/signUp";
    }
}
