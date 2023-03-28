package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/user/create")
//    public String signUpPage() {
//        return "/user/form";
//    }

    /* 문제:
     * 1. 이 코드 그대로 실행하면 매핑 에러 발생 - 위에 /user/create로 매핑해줬던 템플릿이 없어서인 것으로 추측
     * 2. 임시 방편으로 다음 과정을 거쳤더니 되긴 함, 근데 실제로 사용되지 않는 Location을 매핑하는 것이 맞는지 모르겠음.
     *    - 위에 메서드 활성화
     *    - create 메서드 매핑 주소를 안쓰는 location으로 생성해서 html과 연결
     * 3. 위에 메서드를 활성화 하고 create를 PathVariable 받는 형식으로 하면 URI에 파라미터가 생성되지만, 리다이렉팅이 안됨
     */
    @GetMapping("/user/create")
    public String create(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        userService.join(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String listPage() {
        return "/user/list";
    }
}
