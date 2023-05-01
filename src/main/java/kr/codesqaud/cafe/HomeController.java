package kr.codesqaud.cafe;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @ApiOperation(value = "게시글 목록")
    @GetMapping("/")
    public String home() {
        return "forward:/board";
    }
}
