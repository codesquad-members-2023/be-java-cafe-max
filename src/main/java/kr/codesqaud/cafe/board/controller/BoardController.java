package kr.codesqaud.cafe.board.controller;

import kr.codesqaud.cafe.board.domain.BoardPost;
import kr.codesqaud.cafe.board.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/write")
    public String writePost() {
        return "/board/write";
    }

    @PostMapping("/write")
    public String writePost(@ModelAttribute BoardPost boardPost) {
        boardRepository.add(boardPost);
        return "redirect:/";
    }

}
