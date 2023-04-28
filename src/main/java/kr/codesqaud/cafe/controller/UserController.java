package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
//import kr.codesqaud.cafe.domain.UserUpdateDTO;
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
    public String userProfeilUpdate(@PathVariable Long id, Model model)

    {                                   User user = userService.getUserById(id);
            // form에서 새값받는데 빈거에다 안하면 에러날까봐 마침내 DTO 사용 -> ...하고 싶었는데 User로 해도 에러가 안 나는 듯하여 다시 User로
            // DTO는 값 검증에 쓰이는거....??
        model.addAttribute("igo", user); // user건 DTO건 id 값이 있어야 된다는
            // Model은 정보를 뷰에 전달 -> 여기서는 updateForm
            return "user/updateForm"; // (바로 밑에) 딱 여기다가 정보를 보내는 듯 ( {{email}} 등 필드값들...)
    }

    @PutMapping("/users/{id}/update")
    public String userPut(@PathVariable Long id, User user){
//                          , UserUpdateDTO dto){

//        User user = userService.getUserById(id);
//        user.setUserId(dto.getUserId());
//        user.setEmail(dto.getEmail());
//        user.setPassword(dto.getPassword());

        userService.update(user);

        return "redirect:/users";
    }
}
