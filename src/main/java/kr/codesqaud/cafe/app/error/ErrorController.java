package kr.codesqaud.cafe.app.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @GetMapping("/error/client-error")
    public String errorClient() {
        return "error/4xx";
    }

    @GetMapping("/error/server-error")
    public String errorServer() {
        return "error/5xx";
    }
}
