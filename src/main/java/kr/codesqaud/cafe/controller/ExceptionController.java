package kr.codesqaud.cafe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {
    @GetMapping("/fail")
    public String fail() {
        return "exception/fail";
    }
}
