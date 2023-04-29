package kr.codesqaud.cafe.controller;

import kr.codesqaud.cafe.domain.reply.Reply;
import kr.codesqaud.cafe.dto.*;
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
        ArticleResponseDto article = articleService.findByIdx(index);
        model.addAttribute("article", article);
        model.addAttribute("auth", articleService.checkIdentity(article.getUserId(), sessionDto.getId()));
        model.addAttribute("nickname", sessionDto.getName());
        return "qna/show";
    }

    @GetMapping
    public String getIndex(Model model, @RequestParam(defaultValue = "1")int nowPage) {
        Paging paging = articleService.createPaging(nowPage);
        model.addAttribute("first",1);
        model.addAttribute("last",paging.getLastPage());
        model.addAttribute("paging", articleService.pagingList(paging));
        model.addAttribute("articleList", articleService.getAricleList(paging));
        return "index";
    }

    @GetMapping("/article/update/{index}")
    public String getUpdatePage(@PathVariable int index, Model model, HttpSession session) {
        ArticleResponseDto article = articleService.findByIdx(index);
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

    @GetMapping("/article/reply/{index}")
    @ResponseBody
    public List<ReplyResponseDto> getReply(@PathVariable int index, @RequestParam int start) {
        return articleService.replyList(index, start);
    }

    @PostMapping("/article/{index}/reply")
    @ResponseBody
    public Reply saveReply(@PathVariable int index, @RequestParam String contents,
                           HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        return articleService.writeReply(index, contents, sessionDto.getName());
    }

    @DeleteMapping("/article/reply/{index}")
    @ResponseBody
    public boolean deleteReply(@PathVariable int index, HttpSession session) {
        LoginSessionDto sessionDto = (LoginSessionDto) session.getAttribute("sessionId");
        return articleService.deleteReply(index, sessionDto);
    }

    @GetMapping("/getReplyCount/{index}")
    @ResponseBody
    public int getReplyCount(@PathVariable int index) {
        return articleService.replyCount(index);
    }

}
