package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MemberController {

    @PostMapping("/user/create")
    public String create(MemberForm memberform){
        Member member = new Member(memberform.getUserId(), memberform.getPassword(), memberform.getName(), memberform.getEmail());

        // 그리고 만든 member 객체를 저장소에 저장하는 것이 필요.


        System.out.println("id = " + member.getUserId());
        return "redirect:/user/list";
    }
}
