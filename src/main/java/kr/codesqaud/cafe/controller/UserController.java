package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String joinUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "/user/list";
    }
    @GetMapping("/users/{id}")
    public String userProfile(@PathVariable("id") Long id, Model model) {  // ("id") 이거 필요없는거 아닌지 확인 필요
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @GetMapping("/users/{id}/update")  //2단계(선택) 회원 정보 수정하기
    public String userProfeilUpdate(@PathVariable Long id){
        User user = userService.getUserById(id);     // 이 user가 여기 있어야 되는게 맞는지도 그러고보니 불분명(;;) -> (삭제하고 싶다..)
        return "user/updateForm"; //model 삭제: userUpdateForm에는 머스테치가 필요한 부분이 없으므로(..!!)
    }

    @PutMapping("/users/{id}/update")
    public String userPut(@PathVariable Long id, User user){ // << form에서 보낸 값을 받아서.....
        userService.update(user);
        return "redirect:/users";
    }
}
