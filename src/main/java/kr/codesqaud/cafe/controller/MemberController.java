package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.Service.MemberService;
import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping("/user/create")
    public String create(MemberForm memberform){
        Member member = new Member(memberform.getUserId(), memberform.getPassword(), memberform.getName(), memberform.getEmail());

        // 그리고 만든 member 객체를 저장소에 저장하는 것이 필요.
        memberService.signUp(member);

        System.out.println("id = " + memberService.findMemberByUid((long)1).getLoginId());

        return "redirect:/user/list";
    }

    @GetMapping({"/user/list", "user/list.html"})
    public String userList(Model model) { // 모델은 스프링에서 생성해서 넣어준다.
        List<Member> memberList = memberService.findAllMember();
        model.addAttribute("memberlist", memberList);
        System.out.println("Imhere, userlist");
        return "user/list";
    }
}
