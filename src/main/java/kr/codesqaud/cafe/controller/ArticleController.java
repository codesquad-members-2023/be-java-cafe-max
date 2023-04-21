package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.article.Article;
import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.dto.ArticleFormDto;
import kr.codesqaud.cafe.dto.LoginSessionDto;
import kr.codesqaud.cafe.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/article/question")
    public String getWriteForm(HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        articleService.checkLogin(sessionDto);
        return "qna/form";
    }

    @PostMapping("/article/write")
    public String postQuestion(@Valid @ModelAttribute ArticleFormDto articleFormDto, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        articleService.checkLogin(sessionDto);
        articleService.writeArticle(articleFormDto, session);
        return "redirect:/";
    }

    @GetMapping("/article/show/{index}")
    public String getShow(@PathVariable int index, Model model, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        articleService.checkLogin(sessionDto);
        Article article = articleService.findByIdx(index);
        model.addAttribute("article", article);
        model.addAttribute("auth", articleService.checkIdentity(article.getUserId(), sessionDto.getId()));
        model.addAttribute("nickname", sessionDto.getName());
        return "qna/show";
    }

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("articleList", articleService.getAricleList());
        return "index";
    }

    @GetMapping("/article/update/{index}")
    public String getUpdatePage(@PathVariable int index, Model model, HttpSession session) {
        Article article = articleService.findByIdx(index);
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        articleService.checkAuth(article.getUserId(), sessionDto);
        model.addAttribute("setTitle", article.getTitle());
        model.addAttribute("setContent", article.getContents());
        model.addAttribute("index", index);
        return "qna/update_form";
    }

    @PutMapping("/article/update/{index}")
    public String putUpdate(@PathVariable int index, @Valid @ModelAttribute ArticleFormDto dto, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        articleService.update(index, dto, sessionDto.getName());
        return "redirect:/article/show/" + index;
    }

    @DeleteMapping("/article/delete/{index}")
    public String delete(@PathVariable int index) {
        articleService.delete(index);
        return "redirect:/";
    }

    @GetMapping("/article/getReply/{index}")
    @ResponseBody
    public List<Reply> getReply(@PathVariable int index) {
        return articleService.replyList(index);
    }

    @PostMapping("/article/{index}/reply")
    @ResponseBody
    public Reply saveReply(@PathVariable int index, @RequestParam String contents,
                           HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        return articleService.writeReply(index, contents, sessionDto.getName());
    }

    @DeleteMapping("/article/{articleIndex}/delete/{index}")
    @ResponseBody
    public boolean deleteReply(@PathVariable int articleIndex, @PathVariable int index, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        return articleService.deleteReply(articleIndex,index,sessionDto);
    }

}
