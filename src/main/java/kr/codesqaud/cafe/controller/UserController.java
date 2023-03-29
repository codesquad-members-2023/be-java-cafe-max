package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.DTO.UserDTO;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String signUpPage() {
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String create(UserDTO userDTO) {
        User user = new User(); // TODO: 생성자 생성할 때 파람 안받고 setter로 해주는 이유를 모르겠다.
        user.setUserId(userDTO.getUserId()); // TODO: 파람이 많을 때 가독성 좋게 넣어줄 수 있는 방법 찾아서 리팩터링 필요
        user.setPassword(userDTO.getPassword());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        userService.join(user);
        return "redirect:/user/list"; // TODO: 어떤 상황에 템플릿 or 리다이렉팅 해주는지 이해 못했다.
    }

    @GetMapping("/user/list")
    public String listPage() {
        return "/user/list";
    }
}
