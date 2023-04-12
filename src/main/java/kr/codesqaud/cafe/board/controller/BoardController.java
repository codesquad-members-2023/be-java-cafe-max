package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/form")
    public String writeForm(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("sessionUser"));
        return "board/write";
    }

    @PostMapping
    public String writePost(@ModelAttribute PostWriteForm postWriteForm) {
        boardService.write(postWriteForm);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String getPostList(Model model) {
        model.addAttribute("postList", boardService.getPostList());
        return "index";
    }

    @GetMapping("/{postId}")
    public String getDetailPost(@PathVariable Long postId, Model model) {
        model.addAttribute("post", boardService.getPost(postId));
        return "board/detail";
    }

}
