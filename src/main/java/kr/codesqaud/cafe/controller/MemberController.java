package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.member.ProfileEditRequest;
import kr.codesqaud.cafe.dto.member.SignUpRequest;
import kr.codesqaud.cafe.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("memberResponses", memberService.findAll());
        return "member/members";
    }

    @PostMapping
    public String signUp(@ModelAttribute @Validated SignUpRequest signUpRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/signUp";
        }

        memberService.signUp(signUpRequest);
        return "redirect:/members";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable String id, Model model) {
        model.addAttribute("memberResponse", memberService.findById(id));
        return "member/profile";
    }

    @PutMapping("/{id}")
    public String editProfile(@ModelAttribute @Validated ProfileEditRequest profileEditRequest,
        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/profileEdit";
        }

        memberService.update(profileEditRequest);
        redirectAttributes.addAttribute("id", profileEditRequest.getId());
        return "redirect:/members/{id}";
    }

    @GetMapping("/{id}/edit")
    public String profileEditForm(@PathVariable String id, Model model) {
        model.addAttribute("profileEditRequest", ProfileEditRequest.of(memberService.findById(id)));
        return "member/profileEdit";
    }

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute SignUpRequest signUpRequest) {
        return "member/signUp";
    }
}
