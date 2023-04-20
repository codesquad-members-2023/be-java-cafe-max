package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private static final String SIMPLE_FORMS = "simpleForms";
    private static final int DEFAULT_PAGE = 1;
    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String viewIndex(Model model, @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(DEFAULT_PAGE);
        List<SimplePostForm> simpleForms = postService.getAllSimplePostForm(currentPage);
        model.addAttribute(SIMPLE_FORMS, simpleForms);

        PagesInfo pagesInfo = postService.getPagesInfo(currentPage);
        model.addAttribute("pagesInfo", pagesInfo);
        return "index";
    }

}
