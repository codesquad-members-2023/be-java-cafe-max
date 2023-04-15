package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.member.SignUpRequestDto;
import kr.codesqaud.cafe.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String readMember(Model model) {
        model.addAttribute("memberResponsesDto", memberService.findAll());
        return "/all";
    }

    @GetMapping("/join")
    public String joinMember(Model model) {
        model.addAttribute("memberJoinRequestDto", new MemberJoinRequestDto());
        return "/form";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("member") MemberJoinRequestDto memberJoinDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }
        memberService.join(memberJoinDto);
        return "redirect:/join";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("memberLoginRequestDto") @Valid MemberLoginRequestDto memberLoginRequestDto, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        LoginMemberSession loginMember = memberService.login(memberLoginRequestDto);
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("loginMember", loginMember);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("signUpRequestDto") MemberLoginRequestDto memberLoginRequestDto) {
        return "/login";
    }


    @GetMapping("/{memberId}")
    public String profile(@PathVariable Long memberId, Model model) {
        model.addAttribute("memberResponsesDto", memberService.findById(memberId));
        return "/profile";
    }

    @PutMapping("/{memberId}")
    public String editProfile(@ModelAttribute @Valid ProfileEditRequestDto profileEditRequestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/profiledEdit";
        }

        memberService.update(profileEditRequestDto);
        redirectAttributes.addAttribute("memberId", profileEditRequestDto.getMemberId());
        return "redirect:/member/{memberId}";
    }

    @GetMapping("/{memberId}/edit")
    public String profileEditForm(@PathVariable Long memberId, Model model) {
        model.addAttribute("profileEditRequest", ProfileEditRequestDto.of(memberService.findById(memberId)));
        return "/profileEdit";
    }


    @DeleteMapping("/{memberId}")
    public void deleteId(@PathVariable Long memberId) {
        memberService.deleteById(memberId);
    }

    @GetMapping("/{memberId}/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        final HttpSession httpSession = httpServletRequest.getSession();
        httpSession.removeAttribute("loginMember");
        return "home";
    }
}
