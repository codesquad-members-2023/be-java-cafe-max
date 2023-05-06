package kr.codesquad.cafe.post.controller;

import kr.codesquad.cafe.global.PagesInfo;
import kr.codesquad.cafe.post.PostService;
import kr.codesquad.cafe.post.dto.SimplePostForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private static final String SIMPLE_FORMS = "simpleForms";
    private static final String DEFAULT_PAGE = "1";
    private static final String PAGES_INFO = "pagesInfo";
    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String viewIndex(Model model, @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page) {

        List<SimplePostForm> simpleForms = postService.getAllSimplePostForm(page);
        model.addAttribute(SIMPLE_FORMS, simpleForms);

        PagesInfo pagesInfo = postService.getPagesInfo(page);
        model.addAttribute(PAGES_INFO, pagesInfo);
        return "index";
    }

}
