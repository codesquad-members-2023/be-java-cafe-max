package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.dto.ArticleDto;
import kr.codesqaud.cafe.dto.ArticleForm;
import kr.codesqaud.cafe.dto.ReplyDto;
import kr.codesqaud.cafe.dto.ReplyForm;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final ReplyService replyService;

    public ArticleController(ArticleService articleService, ReplyService replyService) {
        this.articleService = articleService;
        this.replyService = replyService;
    }

    @GetMapping
    public String showIndex(Model model) {
        List<ArticleDto> articleDtos = articleService.findAll();
        model.addAttribute("articleDtos", articleDtos);

        return "index";
    }

    @GetMapping("/write")
    public String showWriteForm(ArticleForm articleForm) {
        return "qna/form";
    }

    @PostMapping("/write")
    public String write(ArticleForm articleForm) {
        articleService.saveArticle(articleForm);

        return "redirect:/";
    }

    @GetMapping("/articles/{id}")
    public String showArticleById(@PathVariable("id") long id, Model model, ReplyForm replyForm) {
        ArticleDto articleDto = articleService.findById(id);
        List<ReplyDto> replyDtos = replyService.findAllByArticleId(id);

        model.addAttribute("articleDto", articleDto);
        model.addAttribute("replyDtos", replyDtos);
        return "qna/show";
    }

    @GetMapping("/articles/{id}/update")
    public String showUpdateForm(@PathVariable("id") long id, Model model, ArticleForm articleForm,
                                 HttpServletRequest request) {
        ArticleDto articleDto = articleService.findById(id);
        model.addAttribute("articleDto", articleDto);
        String loginUserId = (String) request.getSession(false).getAttribute("userId");


        if (articleDto.getWriter().equals(loginUserId)) {
            return "qna/update";
        }
        return "redirect:/articles/{id}";
    }

    @PutMapping("/articles/{id}/update")
    public String update(@PathVariable("id") long id, ArticleForm articleForm) {
        articleService.update(articleForm, id);

        return "redirect:/articles/{id}";
    }

    @DeleteMapping("/articles/{id}")
    public String delete(@PathVariable long id, HttpServletRequest request) {
        String loginUserId = (String) request.getSession(false).getAttribute("userId");
        String writer = articleService.findById(id).getWriter();

        if (loginUserId.equals(writer)) {
            articleService.delete(id);
            return "redirect:/";
        }
        return "redirect:/articles/{id}";
    }

    @PostMapping("/articles/{id}/reply")
    public String saveReply(ReplyForm replyForm) {
        replyService.save(replyForm);

        return "redirect:/articles/{id}";
    }
}
