package kr.codesqaud.cafe.controller;

import javax.validation.Valid;
import kr.codesqaud.cafe.dto.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute SignUpRequest signUpRequest) {
        return "member/signUp";
    }

    @PostMapping
    public String signUp(@ModelAttribute @Valid SignUpRequest signUpRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        memberService.signUp(signUpRequest);
        return "member/members";
    }
}
