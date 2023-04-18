package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.service.BoardService;
import kr.codesqaud.cafe.exception.ForbiddenException;
import kr.codesqaud.cafe.user.dto.SessionUser;
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
    public String getDetailPost(@PathVariable Long postId, Model model, HttpSession session) {
        PostResponse postResponse = boardService.getPost(postId);
        model.addAttribute("post", postResponse);

        if (isWriter(session, postResponse.getWriter())) {
            model.addAttribute("isWriter", true);
        }

        return "board/detail";
    }

    @GetMapping("/{postId}/update")
    public String updateForm(@PathVariable Long postId, Model model, HttpSession session) {
        PostResponse postResponse = boardService.getPost(postId);
        model.addAttribute("post", postResponse);

        if (!isWriter(session, postResponse.getWriter())) {
            throw new ForbiddenException("접근할 수 없는 페이지입니다.");
        }

        return "board/update";
    }

    @PutMapping
    public String updatePost(@ModelAttribute PostResponse postResponse) {
        boardService.update(postResponse);
        return "redirect:/board/" + postResponse.getPostId();
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        boardService.delete(postId);
        return "redirect:/board/list";
    }

    private boolean isWriter(HttpSession session, String writer) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        return sessionUser.getUserName().equals(writer);
    }

}
