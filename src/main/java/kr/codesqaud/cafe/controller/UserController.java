package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ErrorMessageDTO;
import kr.codesqaud.cafe.dto.SignUpDTO;
import kr.codesqaud.cafe.service.UserService;
import kr.codesqaud.cafe.validate.SignUpValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final MessageSource messageSource;

    @Autowired
    public UserController(UserService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(new SignUpValidator());
    }

    @GetMapping("/signup")
    public String signup() {
        return "/user/form";
    }

    @PostMapping("/signup")
    public String signup(List<ErrorMessageDTO> errorMessages, Model model) {
        if(!errorMessages.isEmpty()) {
            model.addAttribute("errorMessages", errorMessages);
        }

        return "/user/form";
    }

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", service.findAllUsers());
        return "/user/list";
    }

    @PostMapping("")
    public String userAdd(@Validated SignUpDTO dto, BindingResult result, RedirectAttributes redirect) {
        if(result.hasErrors()) {
            List<ErrorMessageDTO> errorMessages = result.getFieldErrors()
                    .stream()
                    .filter(e -> e.getCode() != null)
                    .map(e -> messageSource.getMessage(e.getCode(), null, Locale.KOREA))
                    .map(ErrorMessageDTO::new)
                    .collect(Collectors.toUnmodifiableList());

            redirect.addFlashAttribute("errorMessages", errorMessages);
            return "redirect:/users/signup";
        }

        service.addUser(dto);
        return "redirect:/users";
    }

}
