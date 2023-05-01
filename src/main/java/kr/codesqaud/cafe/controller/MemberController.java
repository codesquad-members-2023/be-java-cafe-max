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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import kr.codesqaud.cafe.dto.member.MemberJoinRequestDto;
import kr.codesqaud.cafe.dto.member.MemberResponseDto;
import kr.codesqaud.cafe.dto.member.ProfileEditRequestDto;
import kr.codesqaud.cafe.dto.member.MemberLoginRequestDto;
import kr.codesqaud.cafe.exception.common.CommonException;
import kr.codesqaud.cafe.exception.common.CommonExceptionType;
import kr.codesqaud.cafe.service.MemberService;
import kr.codesqaud.cafe.session.LoginMemberSession;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public String readMembers(Model model) {
        model.addAttribute("memberResponsesDto", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/registration")
    public String registerMember(@ModelAttribute MemberJoinRequestDto memberJoinRequestDto) {
        return "member/register";
    }



    @PostMapping
    public String register(@Valid @ModelAttribute MemberJoinRequestDto memberJoinRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/register";
        }
        memberService.join(memberJoinRequestDto);
        return "redirect:/members";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute @Valid MemberLoginRequestDto memberLoginRequestDto, BindingResult bindingResult, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "member/login";
        }

        LoginMemberSession loginMember = memberService.login(memberLoginRequestDto);
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("loginMember", loginMember);

        return "redirect:/posts";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("memberLoginRequestDto") MemberLoginRequestDto memberLoginRequestDto) {
        return "member/login";
    }


    @GetMapping("/{email}")
    public String profile(@PathVariable String email, Model model) {
        MemberResponseDto memberResponseDto = memberService.findByEmail(email);
        model.addAttribute("memberResponsesDto", memberService.findById(memberResponseDto.getMemberId()));
        return "member/profile";
    }

    @PutMapping("/{email}/profile")
    public String editProfile(@PathVariable String email, @Valid ProfileEditRequestDto profileEditRequestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,@SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        if (bindingResult.hasErrors()) {
            return "member/profiledEdit";
        }

        profileEditRequestDto.setMemberId(loginMemberSession.getMemberId());
        memberService.update(profileEditRequestDto);
        redirectAttributes.addAttribute("email", profileEditRequestDto.getEmail());
        return "redirect:/members/{email}";
    }

    @GetMapping("/{email}/profile")
    public String profileEditForm(@PathVariable String email, Model model, @SessionAttribute("loginMember") LoginMemberSession loginMemberSession) {
        if (loginMemberSession.isNotEqualMember(email)) {
            throw new CommonException(CommonExceptionType.ACCESS_DENIED);
        }
        MemberResponseDto memberResponseDto = memberService.findByEmail(email);
        model.addAttribute("profileEditRequestDto", ProfileEditRequestDto.of(memberService.findById(memberResponseDto.getMemberId())));
        return "member/profileEdit";
    }


    @DeleteMapping("/{email}")
    public void deleteId(@PathVariable String email) {
        MemberResponseDto memberResponseDto = memberService.findByEmail(email);
        memberService.deleteById(memberResponseDto.getMemberId());
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/posts";
    }
}
