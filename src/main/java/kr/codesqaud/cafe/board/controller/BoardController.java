package kr.codesqaud.cafe.board.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.codesqaud.cafe.board.dto.CommentResponse;
import kr.codesqaud.cafe.board.dto.PostResponse;
import kr.codesqaud.cafe.board.dto.PostWriteForm;
import kr.codesqaud.cafe.board.paging.PageInfo;
import kr.codesqaud.cafe.board.service.BoardService;
import kr.codesqaud.cafe.board.service.CommentService;
import kr.codesqaud.cafe.exception.ForbiddenException;
import kr.codesqaud.cafe.user.dto.SessionUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Api(tags = {"게시판 API"})
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    @ApiOperation(value = "게시글 작성 페이지 이동")
    @GetMapping("/form")
    public String writeForm(HttpSession session, Model model) {
        model.addAttribute("user", session.getAttribute("sessionUser"));
        return "board/write";
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping
    public String writePost(@ModelAttribute PostWriteForm postWriteForm) {
        boardService.write(postWriteForm);
        return "redirect:/board";
    }

    @ApiOperation(value = "게시글 목록")
    @GetMapping
    public String getPostList(@RequestParam(value = "page", defaultValue = "1") int pageNum, Model model) {
        PageInfo pageInfo = new PageInfo(pageNum, boardService.getTotalCount());
        model.addAttribute("postList", boardService.getPostList(pageInfo));
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }

    @ApiOperation(value = "게시글 상세보기")
    @GetMapping("/{postId}")
    public String getDetailPost(@PathVariable Long postId, @SessionAttribute SessionUser sessionUser, Model model) {
        PostResponse postResponse = boardService.getPost(postId);
        model.addAttribute("post", postResponse);

        String sessionUserName = sessionUser.getUserName();
        if (sessionUserName.equals(postResponse.getWriter().getUserName())) {
            model.addAttribute("isWriter", true);
        }

        List<CommentResponse> commentList = commentService.getCommentListByPostId(postId);
        model.addAttribute("comments", commentList);
        model.addAttribute("commentCount", commentList.size());

        return "board/detail";
    }

    @ApiOperation(value = "게시글 수정 페이지 이동")
    @GetMapping("/{postId}/update")
    public String updateForm(@PathVariable Long postId, @SessionAttribute SessionUser sessionUser,
                             Model model, HttpSession session) {
        PostResponse postResponse = boardService.getPost(postId);
        model.addAttribute("post", postResponse);

        String sessionUserName = sessionUser.getUserName();
        if (!sessionUserName.equals(postResponse.getWriter().getUserName())) {
            throw new ForbiddenException("접근할 수 없는 페이지입니다.");
        }

        return "board/update";
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping
    public String updatePost(@ModelAttribute PostResponse postResponse) {
        boardService.update(postResponse);
        return "redirect:/board/" + postResponse.getPostId();
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId) {
        boardService.delete(postId);
        return "redirect:/board";
    }

}
