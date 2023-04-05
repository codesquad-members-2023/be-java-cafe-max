package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.repository.BoardJdbcRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardJdbcRepository boardRepository;

    public BoardController(BoardJdbcRepository boardJdbcRepository) {
        this.boardRepository = boardJdbcRepository;
    }

    @PostMapping("/write")
    public String writePost(@ModelAttribute PostWriteForm postWriteForm) {
        boardRepository.write(postWriteForm);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("postList", boardRepository.getPostList());
        return "index";
    }

    @GetMapping("/{postId}")
    public String getDetailPost(@PathVariable Long postId, Model model) {
        model.addAttribute("post", boardRepository.getPost(postId));
        return "/board/detail";
    }

}
