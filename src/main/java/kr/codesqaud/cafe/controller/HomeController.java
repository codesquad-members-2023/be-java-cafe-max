package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.article.MemoryArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final MemoryArticleRepository memoryArticleRepository;

    public HomeController(MemoryArticleRepository memoryArticleRepository) {
        this.memoryArticleRepository = memoryArticleRepository;
    }

    @GetMapping(value = {"/", "/index"})
    public String home(Model model) {
        model.addAttribute("articles", memoryArticleRepository.findAll());
        return "index";
    }
}
